package jp.co.blueflag.shisaquick.srv.commonlogic_genka;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * �������Z���X�V
 *  : �������Z��ʂ̏���o�^
 *
 * @author TT.Y.Nishigawa
 * @since  2009/10/28
 *
 */
public class CGEN2020_Logic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public CGEN2020_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �������Z��� �Ǘ�����
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
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
		String msgcd = "";
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 end

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();


			// �g�����U�N�V�����J�n
			super.createExecDB();
			execDB.BeginTran();

			try {
				//�yQP@00342�z
				//�����擾
				ArrayList<?> aryGamen = userInfoData.getId_gamen();
				ArrayList<?> aryKino = userInfoData.getId_kino();
				// ADD 2013/11/5 QP@30154 okano start
				ArrayList<?> aryData = userInfoData.getId_data();
				String DataID = null;
				// ADD 2013/11/5 QP@30154 okano end

				//�������[�v�i�������Z��ʂ̌�����T���j
				for(int i=0; i<aryGamen.size(); i++){
					//���ID�擾
					String gamenId = toString(aryGamen.get(i));

					//�������Z���ID
					if(aryGamen.get(i).equals("170")){

						//�@�\ID�擾
						String kinouID = toString(aryKino.get(i));
						// ADD 2013/11/5 QP@30154 okano start
						DataID = toString(aryData.get(i));
						// ADD 2013/11/5 QP@30154 okano end

						//�ҏW�̏ꍇ
						if(kinouID.equals("20")){
							//�������Z�o�^[kihon]���̍X�V�������s��
							this.torokuKihonUpdateSQL(reqData);

							//�������Z�o�^[memo]���̍X�V�������s��
							this.torokuMemoUpdateSQL(reqData);

							//�������Z�o�^[genryo]���̍X�V�������s��
							this.torokuGenryoUpdateSQL(reqData);

							//�������Z�o�^[keisan]���̍X�V�������s��
							this.torokuKeisanUpdateSQL(reqData);

							//�������Z�o�^[shizai]���̍폜�A�o�^�������s��
							this.torokuShizaiDeleteSQL(reqData);
							this.torokuShizaiInsertSQL(reqData);

							// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
							//�������Z�o�^[kihonsub]���̍X�V�������s��
							this.torokuKihonSubUpdateSQL(reqData);
							// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
						}
						//�{���̏ꍇ
						else if(kinouID.equals("70")){
							//�������Z�o�^[memo]���̍X�V�������s��
							this.torokuMemoUpdateSQL(reqData);
						}
					}
				}

				//�X�e�[�^�X�A�X�e�[�^�X�����X�V
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
//�C���O�\�[�X
//				statusUpdateExec(reqData);
//�C����\�[�X
				// MOD 2013/11/5 QP@30154 okano start
//					msgcd = statusUpdateExec(reqData);
				msgcd = statusUpdateExec(reqData, DataID);
				// MOD 2013/11/5 QP@30154 okano end
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 end

				// �R�~�b�g
				execDB.Commit();

			} catch(Exception e) {
				// ���[���o�b�N
				execDB.Rollback();
				this.em.ThrowException(e, "");

			} finally {

			}

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//����I�����A�Ǘ����ʃp�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
//�C���O�\�[�X
//			this.storageGenkaTorokuDataKanri(resKind.getTableItem(strTableNm));
//�C����\�[�X
			this.storageGenkaTorokuDataKanri(resKind.getTableItem(strTableNm), msgcd);
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 end


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��� �Ǘ����쏈�������s���܂����B");

		} finally {
			if (execDB != null) {
				execDB.Close();				//�Z�b�V�����̃N���[�Y
				execDB = null;
			}
		}
		return resKind;
	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  ExecDB�iDB�X�V�j
	//
	//--------------------------------------------------------------------------------------------------------------
	/**�yQP@00342�z
	 * �X�e�[�^�X�X�V�i�X�e�[�^�X�A�X�e�[�^�X�����e�[�u���j
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
//�C���O�\�[�X
//	private void statusUpdateExec(
//�C����\�[�X
	private String statusUpdateExec(
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 end
				RequestResponsKindBean requestData
				// ADD 2013/11/5 QP@30154 okano start
				,String DataID
				// ADD 2013/11/5 QP@30154 okano end
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
		String MsgCd = "";
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 end

		StringBuffer strInsSql = new StringBuffer();
		try {

			//����NO�擾
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

			//�I���X�e�[�^�X�擾
			String setting = requestData.getFieldVale("kihon", 0, "setting");

			//���݃X�e�[�^�X�擾
			String st_kenkyu = requestData.getFieldVale("kihon", 0, "st_kenkyu");
			String st_seikan = requestData.getFieldVale("kihon", 0, "st_seikan");
			String st_gentyo = requestData.getFieldVale("kihon", 0, "st_gentyo");
			String st_kojo = requestData.getFieldVale("kihon", 0, "st_kojo");
			String st_eigyo = requestData.getFieldVale("kihon", 0, "st_eigyo");

			//���������t���O�擾
			String kenkyu = requestData.getFieldVale("kihon", 0, "busho_kenkyu");
			String seikan = requestData.getFieldVale("kihon", 0, "busho_seikan");
			String gentyo = requestData.getFieldVale("kihon", 0, "busho_gentyo");
			String kojo = requestData.getFieldVale("kihon", 0, "busho_kojo");
			String eigyo = requestData.getFieldVale("kihon", 0, "busho_eigyo");

			//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
			// �X�e�[�^�X����Insert�p�����[�^�쐬�p�z��
			// ���[�U��� �i��ЁA�����A���[�UID�j
			String aryUsrInf[] = {userInfoData.getCd_kaisha(), userInfoData.getCd_busho(), userInfoData.getId_user()};
			// ����No �i�Ј��A�N�A�ǔԁA�}�ԁj
			String aryShisakuNo[] = {cd_shain, nen, no_oi, no_eda};
			// �ݒ�X�e�[�^�X�i�����l�F���݂̃X�e�[�^�X�j
			String aryStatus[] = {st_kenkyu, st_seikan, st_gentyo, st_kojo, st_eigyo};
			//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

			//������------------------------------------------------------------------------
			if(kenkyu.equals("1")){
				//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
				// �H��ύX
				if(setting.equals("9")){
					// �������F���ۑ��i�H��ύX�j
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kenkyu", "9");
					this.execDB.execSQL(strInsSql.toString());
				} else {
				// setting�� "0"�ȊO�̎����u���ۑ��v�ŗ�����ǉ�����i�X�e�[�^�X�ݒ��׼޵���݂��Ȃ��̂ŁAsetting=undefined�j
				//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

				//���ۑ�
				//if(setting.equals("0")){
					//�X�e�[�^�X�e�[�u���X�V
					//�X�V���Ȃ�

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_kenkyu'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// �������F���ۑ�
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kenkyu", "0");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
			}

			//���Y�Ǘ���----------------------------------------------------------------------
			else if(seikan.equals("1")){
				//���ۑ�
				if(setting.equals("0")){
					//�X�e�[�^�X�e�[�u���X�V
					//�X�V���Ȃ�

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_seisan'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// ���Y�Ǘ����F���ۑ�
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "0");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());

				//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
				// �H��ύX
				} else if(setting.equals("9")){
					// ���Y�Ǘ����F���ۑ��i�H��ύX�j
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "9");
					this.execDB.execSQL(strInsSql.toString());
				//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

				}
				//���Z�˗�
				else if(setting.equals("1")){

					//�X�e�[�^�X�e�[�u���X�V
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_seisan = 2");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_seisan'");
//					strInsSql.append("            ,'1' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ,2 ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// ���Y�Ǘ����F���Z�˗�
					aryStatus[1] = "2";		//����
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "1");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());

//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
					MsgCd = "RGEN0042";
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 end
				}
				//�m�F����
				else if(setting.equals("2")){
					// ADD 2013/11/5 QP@30154 okano start
					// ��ʌ����i170�j�ADataId==1 �i�O���[�v�i���ǁj�j�� �����A�H��̽ð�� ���u�����v��
					if(DataID.equals("1")){
						//�X�e�[�^�X�e�[�u���X�V
						strInsSql.append(" UPDATE tr_shisan_status ");
						strInsSql.append("    SET  ");
						strInsSql.append("        st_seisan   = 3");
						strInsSql.append("       ,st_gensizai = 2");
						strInsSql.append("       ,st_kojo     = 2");
						strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
						strInsSql.append("       ,dt_koshin = GETDATE()");
						strInsSql.append("  WHERE ");
						strInsSql.append(" 	  cd_shain =  " + cd_shain);
						strInsSql.append("       AND nen =  " + nen);
						strInsSql.append("       AND no_oi =  " + no_oi);
						strInsSql.append("       AND no_eda =  " + no_eda);
						this.execDB.execSQL(strInsSql.toString());


						strInsSql = null;
						strInsSql = new StringBuffer();

						//�X�e�[�^�X����ǉ�
						//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//						strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//						strInsSql.append("            (cd_shain ");
//						strInsSql.append("            ,nen ");
//						strInsSql.append("            ,no_oi ");
//						strInsSql.append("            ,no_eda ");
//						strInsSql.append("            ,dt_henkou ");
//						strInsSql.append("            ,cd_kaisha ");
//						strInsSql.append("            ,cd_busho ");
//						strInsSql.append("            ,id_henkou ");
//						strInsSql.append("            ,cd_zikko_ca ");
//						strInsSql.append("            ,cd_zikko_li ");
//						strInsSql.append("            ,st_kenkyu ");
//						strInsSql.append("            ,st_seisan ");
//						strInsSql.append("            ,st_gensizai ");
//						strInsSql.append("            ,st_kojo ");
//						strInsSql.append("            ,st_eigyo ");
//						strInsSql.append("            ,id_toroku ");
//						strInsSql.append("            ,dt_toroku) ");
//						strInsSql.append("      VALUES ");
//						strInsSql.append("            (" + cd_shain + " ");
//						strInsSql.append("            ," + nen + " ");
//						strInsSql.append("            ," + no_oi + " ");
//						strInsSql.append("            ," + no_eda +" ");
//						strInsSql.append("            ,GETDATE() ");
//						strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//						strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,'wk_seisan'");
//						strInsSql.append("            ,'2' ");
//						strInsSql.append("            ," + st_kenkyu + " ");
//						strInsSql.append("            ,3 ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ," + st_eigyo + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,GETDATE() )");
						//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

						//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
						// ���Y�Ǘ����F�m�F����
						aryStatus[1] = "3";		//����
						aryStatus[2] = "2";		//����
						aryStatus[3] = "2";		//�H��
						strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "2");
						//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

						this.execDB.execSQL(strInsSql.toString());
					} else {
					// ADD 2013/11/5 QP@30154 okano end
					//�X�e�[�^�X�e�[�u���X�V
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_seisan = 3");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_seisan'");
//					strInsSql.append("            ,'2' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ,3 ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// ���Y�Ǘ����F�m�F����
					aryStatus[1] = "3";		//����
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "2");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());
					// ADD 2013/11/5 QP@30154 okano start
					}
					// ADD 2013/11/5 QP@30154 okano end

					//20160607  KPX@1502111_No7 ADD start
					strInsSql = null;
					strInsSql = new StringBuffer();
					// SQL���̍쐬
					strInsSql.append(" UPDATE tr_shisan_shisaku ");
					strInsSql.append("    SET  ");
					strInsSql.append("        fg_koumokuchk = 1");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());
					//20160607  KPX@1502111_No7 ADD end

//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
					MsgCd = "RGEN0043";
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 end

				}
				//�m�F�����
				else if(setting.equals("3")){
					//�X�e�[�^�X�e�[�u���X�V
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_seisan = 2");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_seisan'");
//					strInsSql.append("            ,'3' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ,2 ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// ���Y�Ǘ����F�m�F�����
					aryStatus[1] = "2";		//����
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "3");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());
				}
			}

			//�����ޒ��B��---------------------------------------------------------------------
			else if(gentyo.equals("1")){
				//���ۑ�
				if(setting.equals("0")){
					//�X�e�[�^�X�e�[�u���X�V
					//�X�V���Ȃ�

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_genshizai'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// �����ޒ��B���F���ۑ�
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_genshizai", "0");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
				//�m�F����
				else if(setting.equals("2")){
					// ADD 2013/11/5 QP@30154 okano start
					if(DataID.equals("2")){
						//�X�e�[�^�X�e�[�u���X�V
						strInsSql.append(" UPDATE tr_shisan_status ");
						strInsSql.append("    SET  ");
						strInsSql.append("        st_gensizai = 2");
						strInsSql.append("       ,st_kojo     = 2");
						strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
						strInsSql.append("       ,dt_koshin = GETDATE()");
						strInsSql.append("  WHERE ");
						strInsSql.append(" 	  cd_shain =  " + cd_shain);
						strInsSql.append("       AND nen =  " + nen);
						strInsSql.append("       AND no_oi =  " + no_oi);
						strInsSql.append("       AND no_eda =  " + no_eda);
						this.execDB.execSQL(strInsSql.toString());


						strInsSql = null;
						strInsSql = new StringBuffer();

						//�X�e�[�^�X����ǉ�
						//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//						strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//						strInsSql.append("            (cd_shain ");
//						strInsSql.append("            ,nen ");
//						strInsSql.append("            ,no_oi ");
//						strInsSql.append("            ,no_eda ");
//						strInsSql.append("            ,dt_henkou ");
//						strInsSql.append("            ,cd_kaisha ");
//						strInsSql.append("            ,cd_busho ");
//						strInsSql.append("            ,id_henkou ");
//						strInsSql.append("            ,cd_zikko_ca ");
//						strInsSql.append("            ,cd_zikko_li ");
//						strInsSql.append("            ,st_kenkyu ");
//						strInsSql.append("            ,st_seisan ");
//						strInsSql.append("            ,st_gensizai ");
//						strInsSql.append("            ,st_kojo ");
//						strInsSql.append("            ,st_eigyo ");
//						strInsSql.append("            ,id_toroku ");
//						strInsSql.append("            ,dt_toroku) ");
//						strInsSql.append("      VALUES ");
//						strInsSql.append("            (" + cd_shain + " ");
//						strInsSql.append("            ," + nen + " ");
//						strInsSql.append("            ," + no_oi + " ");
//						strInsSql.append("            ," + no_eda +" ");
//						strInsSql.append("            ,GETDATE() ");
//						strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//						strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,'wk_genshizai'");
//						strInsSql.append("            ,'1' ");
//						strInsSql.append("            ," + st_kenkyu + " ");
//						strInsSql.append("            ," + st_seikan + " ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ," + st_eigyo + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,GETDATE() )");
						//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

						//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
						// �����ޒ��B���F�m�F����
						aryStatus[2] = "2";		//����
						aryStatus[3] = "2";		//�H��
						strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_genshizai", "1");
						//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

						this.execDB.execSQL(strInsSql.toString());
					} else {
					// ADD 2013/11/5 QP@30154 okano end
					//�X�e�[�^�X�e�[�u���X�V
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_gensizai = 2");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_genshizai'");
//					strInsSql.append("            ,'1' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ,2 ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// �����ޒ��B���F�m�F����
					aryStatus[2] = "2";		//����
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_genshizai", "1");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());
					// ADD 2013/11/5 QP@30154 okano start
					}
					// ADD 2013/11/5 QP@30154 okano end

//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
					MsgCd = "RGEN0045";
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 end

				}
				//�m�F�����
				else if(setting.equals("3")){
					//�X�e�[�^�X�e�[�u���X�V
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_gensizai = 1");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_genshizai'");
//					strInsSql.append("            ,'2' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ,1 ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// �����ޒ��B���F�m�F�����
					aryStatus[3] = "1";		//����
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_genshizai", "2");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
			}

			//�H��-------------------------------------------------------------------------
			else if(kojo.equals("1")){
				//���ۑ�
				if(setting.equals("0")){
					//�X�e�[�^�X�e�[�u���X�V
					//�X�V���Ȃ�

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_kojo'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// �H��F���ۑ�
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kojo", "0");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
				//�m�F����
				else if(setting.equals("2")){
					// ADD 2013/11/5 QP@30154 okano start
					if(DataID.equals("2")){
						//�X�e�[�^�X�e�[�u���X�V
						strInsSql.append(" UPDATE tr_shisan_status ");
						strInsSql.append("    SET  ");
						strInsSql.append("        st_gensizai = 2");
						strInsSql.append("       ,st_kojo     = 2");
						strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
						strInsSql.append("       ,dt_koshin = GETDATE()");
						strInsSql.append("  WHERE ");
						strInsSql.append(" 	  cd_shain =  " + cd_shain);
						strInsSql.append("       AND nen =  " + nen);
						strInsSql.append("       AND no_oi =  " + no_oi);
						strInsSql.append("       AND no_eda =  " + no_eda);
						this.execDB.execSQL(strInsSql.toString());


						strInsSql = null;
						strInsSql = new StringBuffer();

						//�X�e�[�^�X����ǉ�
						//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//						strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//						strInsSql.append("            (cd_shain ");
//						strInsSql.append("            ,nen ");
//						strInsSql.append("            ,no_oi ");
//						strInsSql.append("            ,no_eda ");
//						strInsSql.append("            ,dt_henkou ");
//						strInsSql.append("            ,cd_kaisha ");
//						strInsSql.append("            ,cd_busho ");
//						strInsSql.append("            ,id_henkou ");
//						strInsSql.append("            ,cd_zikko_ca ");
//						strInsSql.append("            ,cd_zikko_li ");
//						strInsSql.append("            ,st_kenkyu ");
//						strInsSql.append("            ,st_seisan ");
//						strInsSql.append("            ,st_gensizai ");
//						strInsSql.append("            ,st_kojo ");
//						strInsSql.append("            ,st_eigyo ");
//						strInsSql.append("            ,id_toroku ");
//						strInsSql.append("            ,dt_toroku) ");
//						strInsSql.append("      VALUES ");
//						strInsSql.append("            (" + cd_shain + " ");
//						strInsSql.append("            ," + nen + " ");
//						strInsSql.append("            ," + no_oi + " ");
//						strInsSql.append("            ," + no_eda +" ");
//						strInsSql.append("            ,GETDATE() ");
//						strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//						strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,'wk_kojo'");
//						strInsSql.append("            ,'1' ");
//						strInsSql.append("            ," + st_kenkyu + " ");
//						strInsSql.append("            ," + st_seikan + " ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ," + st_eigyo + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,GETDATE() )");
						//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

						//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
						// �H��F�m�F����
						aryStatus[2] = "2";		// ����
						aryStatus[3] = "2";		// �H��
						strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kojo", "1");
						//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

						this.execDB.execSQL(strInsSql.toString());
					} else {
					// ADD 2013/11/5 QP@30154 okano end
					//�X�e�[�^�X�e�[�u���X�V
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_kojo = 2");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_kojo'");
//					strInsSql.append("            ,'1' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ,2 ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// �H��F�m�F����
					aryStatus[3] = "2";		//�H��
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kojo", "1");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());
					// ADD 2013/11/5 QP@30154 okano start
					}
					// ADD 2013/11/5 QP@30154 okano end

//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
					MsgCd = "RGEN0044";
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 end

				}
				//�m�F�����
				else if(setting.equals("3")){
					//�X�e�[�^�X�e�[�u���X�V
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_kojo = 1");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_kojo'");
//					strInsSql.append("            ,'2' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ,1 ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// �H��F�m�F�����
					aryStatus[3] = "1";		//�H��
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kojo", "2");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
			}

			//�c��-------------------------------------------------------------------------
			else if(eigyo.equals("1")){
				//���ۑ�
				if(setting.equals("0")){
					//�X�e�[�^�X�e�[�u���X�V
					//�X�V���Ȃ�

					//�X�e�[�^�X����ǉ�
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_eigyo'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
					// �c�ƁF���ۑ�
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_eigyo", "0");
					//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strInsSql = null;

		}

//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
		return MsgCd;
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 end
	}

//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
	/**�yQP@40812�z
	 * ���N�G�X�g�f�[�^����̐ݒ�l���A�X�e�[�^�X����INSERT SQL�𐶐�����
	 * @param argUserInfo   : ���[�U���  [0]cd_kaisha [1]cd_busho [2]id_user
	 * @param argSisakuNo   : ����No      [0]cd_shain  [1]nen [2]no_oi [3]no_eda
	 * @param argStatus     : �X�e�[�^�X  [0]st_kenkyu [1]st_seikan [2]st_gentyo [3]st_kojo [4]st_eigyo
	 * @param argZikkoCa    : ��Ǝ�ʁi�����j
	 * @param argZikkoLi    : ��Əڍ׋Ɩ��i���e�����R�[�h�j
	 * @return StringBuffer : InsertSQL
	 */
	private StringBuffer MakeStatusRirekiSQLBuf(String[] argUserInfo, String[] argSisakuNo, String[] argStatus
			, String argZikkoCa, String argZikkoLi) {
		// �߂�l�̐錾
		StringBuffer ret = new StringBuffer();

		//�X�e�[�^�X����ǉ�SQL �쐬
		ret.append(" INSERT INTO tr_shisan_status_rireki ");
		ret.append("            (cd_shain ");
		ret.append("            ,nen ");
		ret.append("            ,no_oi ");
		ret.append("            ,no_eda ");
		ret.append("            ,dt_henkou ");
		ret.append("            ,cd_kaisha ");
		ret.append("            ,cd_busho ");
		ret.append("            ,id_henkou ");
		ret.append("            ,cd_zikko_ca ");
		ret.append("            ,cd_zikko_li ");
		ret.append("            ,st_kenkyu ");
		ret.append("            ,st_seisan ");
		ret.append("            ,st_gensizai ");
		ret.append("            ,st_kojo ");
		ret.append("            ,st_eigyo ");
		ret.append("            ,id_toroku ");
		ret.append("            ,dt_toroku) ");
		ret.append("      VALUES ");
		ret.append("            (" + argSisakuNo[0] + " ");
		ret.append("            ," + argSisakuNo[1] + " ");
		ret.append("            ," + argSisakuNo[2] + " ");
		ret.append("            ," + argSisakuNo[3] +" ");
		ret.append("            ,GETDATE() ");
		ret.append("            ," + argUserInfo[0] + " ");
		ret.append("            ," + argUserInfo[1] + " ");
		ret.append("            ," + argUserInfo[2] + " ");
		ret.append("            ,'" + argZikkoCa + "'");
		ret.append("            ,'" + argZikkoLi + "'");
		ret.append("            ," + argStatus[0] + " ");
		ret.append("            ," + argStatus[1] + " ");
		ret.append("            ," + argStatus[2] + " ");
		ret.append("            ," + argStatus[3] + " ");
		ret.append("            ," + argStatus[4] + " ");
		ret.append("            ," + argUserInfo[2] + " ");
		ret.append("            ,GETDATE() )");

		return ret;
	}
//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

	/**�yQP@00342�z
	 * �������Z�o�^[memo]���̍X�V����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuMemoUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//�yQP@00342�z
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

			//�yQP@00342�z
			//�������Z�����̍X�V
			StringBuffer strSQL_memo = new StringBuffer();
			strSQL_memo.append(" UPDATE tr_shisan_memo ");
			strSQL_memo.append("    SET memo = '" + toString( reqData.getFieldVale( "kihon", 0, "memo_genkashisan"),"") + "'");
			strSQL_memo.append("       ,id_koshin = " + userInfoData.getId_user());
			strSQL_memo.append("       ,dt_koshin = GETDATE() ");
			strSQL_memo.append("  WHERE  ");
			strSQL_memo.append("	cd_shain = "    + strReqShainCd);
			strSQL_memo.append("    AND nen = "   + strReqNen);
			strSQL_memo.append("    AND no_oi = " + strReqOiNo);
			this.execDB.execSQL(strSQL_memo.toString());

			//�������Z�����i�c�ƘA���p�j�̍X�V
			StringBuffer strSQL_memo_eigyo = new StringBuffer();
			strSQL_memo_eigyo.append(" UPDATE tr_shisan_memo_eigyo ");
			strSQL_memo_eigyo.append("    SET memo_eigyo = '" + toString( reqData.getFieldVale( "kihon", 0, "memo_genkashisan_eigyo"),"") + "'");
			strSQL_memo_eigyo.append("       ,id_koshin = " + userInfoData.getId_user());
			strSQL_memo_eigyo.append("       ,dt_koshin = GETDATE() ");
			strSQL_memo_eigyo.append("  WHERE  ");
			strSQL_memo_eigyo.append("	cd_shain = "    + strReqShainCd);
			strSQL_memo_eigyo.append("    AND nen = "   + strReqNen);
			strSQL_memo_eigyo.append("    AND no_oi = " + strReqOiNo);
			this.execDB.execSQL(strSQL_memo_eigyo.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�o�^[memo]���̍X�VSQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}
	}


	/**
	 * �������Z�o�^[kihon]���̍X�V����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuKihonUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQLSearch = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//�yQP@00342�z
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

			//�yQP@00342�z
//			//--------------------- �X�V�ӏ��̌����i���Y�Ǘ��A�����ޒ��B�A�H��j ---------------------
//			String id_seisanKanri = "";  		//���Y�Ǘ��X�V��
//			String dt_seisanKanri = "";   	//���Y�Ǘ��X�V��
//			String id_genshizai = "";			//�����ޒ��B�X�V��
//			String dt_genshizai = "";      	//�����ޒ��B�X�V��
//			String id_kojo = "";				//�H��X�V��
//			String dt_kojo = "";				//�H��X�V��
//
//			//����SQL������
//			strSQLSearch = new StringBuffer();
//			strSQLSearch.append(" SELECT DISTINCT cd_category ");
//			strSQLSearch.append(" FROM ma_literal ");
//			strSQLSearch.append(" WHERE ");
//			strSQLSearch.append("  (cd_category = 'K_Busho_Seisankanri' ");
//			strSQLSearch.append("   AND value1 = '" + userInfoData.getCd_kaisha() + "' ");
//			strSQLSearch.append("   AND value2 = '" + userInfoData.getCd_busho() + "') ");
//			strSQLSearch.append(" OR ");
//			strSQLSearch.append(" (cd_category = 'K_Busho_Genshizai' ");
//			strSQLSearch.append("  AND value1 = '" + userInfoData.getCd_kaisha() + "' ");
//			strSQLSearch.append("  AND value2 = '" + userInfoData.getCd_busho() + "') ");
//			strSQLSearch.append(" OR ");
//			strSQLSearch.append(" (cd_category = 'K_Busho_Kojyo' ");
//			strSQLSearch.append("  AND value1 = '" + userInfoData.getCd_kaisha() + "' ");
//			strSQLSearch.append("  AND value2 = '" + userInfoData.getCd_busho() + "') ");
//
//			//�������s
//			createSearchDB();
//			lstRecset = this.searchDB.dbSearch(strSQLSearch.toString());
//
//			//�������ʂɂĊe�X�V���A�X�V�Ґݒ�
//			for( int i=0; i<lstRecset.size(); i++ ){
//
//				//���Y�Ǘ��̏ꍇ
//				if( toString(lstRecset.get(i)).equals("K_Busho_Seisankanri") ){
//					id_seisanKanri = userInfoData.getId_user();
//					dt_seisanKanri = "GETDATE()";
//				}
//
//				//�����ޒ��B�̏ꍇ
//				if( toString(lstRecset.get(i)).equals("K_Busho_Genshizai") ){
//					id_genshizai = userInfoData.getId_user();
//					dt_genshizai = "GETDATE()";
//				}
//
//				//�H��̏ꍇ
//				if( toString(lstRecset.get(i)).equals("K_Busho_Kojyo") ){
//					id_kojo = userInfoData.getId_user();
//					dt_kojo = "GETDATE()";
//				}
//
//			}
//
//			//-------------------------------- �T���v��NO�m��X�V�� -------------------------------
//			String sam_id_koshin = "";
//
//			//�����pSQL����
//			strSQLSearch = new StringBuffer();
//			strSQLSearch.append(" SELECT saiyo_sample ");
//			strSQLSearch.append(" FROM tr_shisan_shisakuhin ");
//			strSQLSearch.append(" WHERE  ");
//			strSQLSearch.append(" 	cd_shain = " + strReqShainCd );
//			strSQLSearch.append(" 	AND nen = " + strReqNen );
//			strSQLSearch.append(" 	AND no_oi = " + strReqOiNo );
//			//�yQP@00342�z
//			strSQLSearch.append(" 	AND no_eda = " + strReqEdaNo );
//
//			//�������s
//			createSearchDB();
//			lstRecset = this.searchDB.dbSearch(strSQLSearch.toString());
//
//			//�T���v��NO�̕ύX���Ȃ��ꍇ
//			if( toString(lstRecset.get(0)).equals(toString( reqData.getFieldVale( "kihon", 0, "no_sanpuru"))) ){
//
//			}
//			//�T���v��NO�̕ύX������ꍇ
//			else{
//				sam_id_koshin = userInfoData.getId_user();
//
//			}



			//�X�V�pSQL�쐬
			//�yQP@00342�z
//			strSQL.append(" UPDATE tr_shisan_shisakuhin");
//			strSQL.append("   SET ");
//			strSQL.append("        saiyo_sample = " + toString( reqData.getFieldVale( "kihon", 0, "no_sanpuru"),"null"));
//			//strSQL.append("       ,cd_kaisha = "      + toString( reqData.getFieldVale( "kihon", 0, "cd_kaisya"),"null"));
//			//strSQL.append("       ,cd_kojo = "         + toString( reqData.getFieldVale( "kihon", 0, "cd_kojyo"),"null"));
//			strSQL.append("       ,su_iri = '"           + toString( reqData.getFieldVale( "kihon", 0, "irisu"),"",",") + "'");
//			strSQL.append("       ,cd_nisugata = '"   + toString( reqData.getFieldVale( "kihon", 0, "nisugata"),"") + "'");
//			strSQL.append("       ,genka = '"          + toString( reqData.getFieldVale( "kihon", 0, "kibo_genka"),"",",") + "'");
//			strSQL.append("       ,cd_genka_tani = '" + toString (reqData.getFieldVale( "kihon", 0, "kibo_genka_cd_tani"),"") + "'");
//			strSQL.append("       ,baika = '"           + toString( reqData.getFieldVale( "kihon", 0, "kibo_baika"),"",",") + "'");
//			strSQL.append("       ,buturyo = '"         + toString( reqData.getFieldVale( "kihon", 0, "butu_sotei"),"") + "'");
//			strSQL.append("       ,dt_hatubai = '"     + toString( reqData.getFieldVale( "kihon", 0, "ziki_hanbai"),"") + "'");
//			strSQL.append("       ,uriage_k = '"        + toString( reqData.getFieldVale( "kihon", 0, "keikaku_uriage"),"") + "'");
//			strSQL.append("       ,rieki_k = '"          + toString( reqData.getFieldVale( "kihon", 0, "keikaku_rieki"),"") + "'");
//			strSQL.append("       ,uriage_h = '"        + toString( reqData.getFieldVale( "kihon", 0, "hanbaigo_uriage"),"") + "'");
//			strSQL.append("       ,rieki_h = '"          + toString( reqData.getFieldVale( "kihon", 0, "hanbaigo_rieki"),"") + "'");
//			strSQL.append("       ,lot = '"                + toString( reqData.getFieldVale( "kihon", 0, "seizo_roto"),"") + "'");
//			strSQL.append("       ,memo = '"           + toString( reqData.getFieldVale( "kihon", 0, "memo_genkashisan"),"") + "'");
//			strSQL.append("      ,fg_keisan = "        + toString( reqData.getFieldVale( "kihon", 0, "ragio_kesu_kg"),"null"));
//			strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
//			strSQL.append("     ,dt_koshin = GETDATE()");
//
//			if(!id_seisanKanri.equals("")){
//				strSQL.append("     ,sei_id_koshin = " + toString( id_seisanKanri , "null" ) );
//				strSQL.append("     ,sei_dt_koshin = " + toString( dt_seisanKanri , "null" ) );
//			}
//			if(!id_genshizai.equals("")){
//				strSQL.append("     ,gen_id_koshin = " + toString( id_genshizai , "null" ) );
//				strSQL.append("     ,gen_dt_koshin = " + toString( dt_genshizai , "null" ) );
//			}
//			if(!id_kojo.equals("")){
//				strSQL.append("     ,kojo_id_koshin = " + toString( id_kojo , "null" ) );
//				strSQL.append("     ,kojo_dt_koshin = " + toString( dt_kojo , "null" ) );
//			}
//
//			if(!toString( sam_id_koshin ).equals("")){
//				strSQL.append("     ,sam_dt_koshin = GETDATE()");
//				strSQL.append("     ,sam_id_koshin = " + sam_id_koshin );
//			}
			strSQL.append(" UPDATE tr_shisan_shisakuhin");
			strSQL.append("   SET ");
			strSQL.append("       lot = '"                + toString( reqData.getFieldVale( "kihon", 0, "seizo_roto"),"") + "'");
			strSQL.append("      ,fg_keisan = "        + toString( reqData.getFieldVale( "kihon", 0, "ragio_kesu_kg"),"null"));
			strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
			strSQL.append("     ,dt_koshin = GETDATE()");


			strSQL.append(" WHERE");
			strSQL.append("	cd_shain = "    + strReqShainCd);
			strSQL.append("    AND nen = "   + strReqNen);
			strSQL.append("    AND no_oi = " + strReqOiNo);

			//�yQP@00342�z
			strSQL.append(" 	AND no_eda = " + strReqEda );


			//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�o�^[kihon]���̍X�VSQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQLSearch = null;

			//�Z�b�V�����̃N���[�Y
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

		}
	}



	/**
	 * �������Z�o�^[genryo]���̍X�V����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuGenryoUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQLSearch = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//DB�R�l�N�V����
			createSearchDB();

			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//�yQP@00342�z
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");


			//�X�V����
			for ( int i=0; i<reqData.getCntRow("genryo"); i++ ) {

				//�J���}�폜
				String genka = toString(reqData.getFieldVale("genryo", i, "tanka"),"",",");
				String budomari = toString(reqData.getFieldVale("genryo", i, "budomari"),"",",");


				//------------------------ �P����r�@�������� -------------------------
				strSQLSearch = new StringBuffer();

				//�P���@���͒l�E�}�X�^�l��r
				strSQLSearch.append(" select ");
				strSQLSearch.append("  Count(tanka_ma) AS CNT ");
				strSQLSearch.append(" from ");
				strSQLSearch.append("  tr_shisan_haigo ");
				strSQLSearch.append(" where ");
				strSQLSearch.append("  cd_shain = " + strReqShainCd );
				strSQLSearch.append("  AND nen = " + strReqNen );
				strSQLSearch.append("  AND no_oi = " + strReqOiNo );

				//�yQP@00342�z
				strSQLSearch.append("  AND no_eda = " + strReqEda );

				strSQLSearch.append("  AND cd_kotei = " + reqData.getFieldVale("genryo", i, "cd_kotei") );
				strSQLSearch.append("  AND seq_kotei = " + reqData.getFieldVale("genryo", i, "seq_kotei") );
				strSQLSearch.append("  AND tanka_ma = " + toString(genka,"null") );

				lstRecset = this.searchDB.dbSearch(strSQLSearch.toString());

				//�}�X�^�l�Ɠ����ꍇ
				if( toString(lstRecset.get(0)).equals("1") ){
					genka = "";
				}


				//------------------------ ������r�@�������� -------------------------
				strSQLSearch = new StringBuffer();

				//�����@���͒l�E�}�X�^�l��r
				strSQLSearch.append(" select ");
				strSQLSearch.append("  Count(budomar_ma) AS CNT ");
				strSQLSearch.append(" from ");
				strSQLSearch.append("  tr_shisan_haigo ");
				strSQLSearch.append(" where ");
				strSQLSearch.append("  cd_shain = " + strReqShainCd );
				strSQLSearch.append("  AND nen = " + strReqNen );
				strSQLSearch.append("  AND no_oi = " + strReqOiNo );

				//�yQP@00342�z
				strSQLSearch.append("  AND no_eda = " + strReqEda );

				strSQLSearch.append("  AND cd_kotei = " + reqData.getFieldVale("genryo", i, "cd_kotei") );
				strSQLSearch.append("  AND seq_kotei = " + reqData.getFieldVale("genryo", i, "seq_kotei") );
				strSQLSearch.append("  AND budomar_ma = " + toString(budomari,"null") );

				lstRecset = this.searchDB.dbSearch(strSQLSearch.toString());

				//�}�X�^�l�Ɠ����ꍇ
				if( toString(lstRecset.get(0)).equals("1") ){
					budomari = "";
				}


				//----------------------------- �X�V���� ----------------------------
				strSQL = new StringBuffer();

				strSQL.append(" UPDATE tr_shisan_haigo");
				strSQL.append("   SET ");
				strSQL.append("      tanka_ins = " + toString(genka,"null"));
				strSQL.append("      ,budomari_ins = " + toString(budomari,"null"));
				strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
				strSQL.append("      ,dt_koshin = GETDATE()");
				strSQL.append(" WHERE");
				strSQL.append("	cd_shain = "    + strReqShainCd);
				strSQL.append("   AND nen = "   + strReqNen);
				strSQL.append("   AND no_oi = " + strReqOiNo);

				//�yQP@00342�z
				strSQL.append("  AND no_eda = " + strReqEda );

				strSQL.append("   AND cd_kotei = " + reqData.getFieldVale("genryo", i, "cd_kotei"));
				strSQL.append("   AND seq_kotei = " + reqData.getFieldVale("genryo", i, "seq_kotei"));

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̍폜���s���B
				this.execDB.execSQL(strSQL.toString());
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�o�^[genryo]���̍X�VSQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQLSearch = null;

			//�Z�b�V�����̃N���[�Y
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

		}
	}

	/**
	 * �������Z�o�^[keisan]���̍X�V����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuKeisanUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//�yQP@00342�z
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

			//�I���X�e�[�^�X�擾
			String setting = reqData.getFieldVale("kihon", 0, "setting");
			//���������t���O
			String kojo = reqData.getFieldVale("kihon", 0, "busho_kojo");
			//���݃X�e�[�^�X
			String st_kojo = reqData.getFieldVale("kihon", 0, "st_kojo");

			//�X�V�pSQL�쐬
			for ( int i=0; i<reqData.getCntRow("keisan"); i++ ) {

				//�yQP@00342�z���Z���~
				String fg_chusi = toString(reqData.getFieldVale("keisan", i, "fg_chusi"));

				//���Z����NULL�ݒ�
				String shisanHi = toString(reqData.getFieldVale("keisan", i, "shisan_date"),"null");

				//�yH24�N�x�Ή��zNo.11 Start
				if(kojo.equals("1")){
					if(setting.equals("2")||st_kojo.equals("2")){
						// MOD start 20120614 hisahori ���Z�����󔒂̃T���v���̂ݓ��t���or�X�V
						//shisanHi = "CONVERT(varchar, GETDATE(),111)";
						if(shisanHi.equals("null")){
							shisanHi = "CONVERT(varchar, GETDATE(),111)";
						}else{
							shisanHi = "'" + shisanHi + "'";
						}
						// MOD end 20120614 hisahori
					}else{
						if(shisanHi.equals("null")){
							//�������Ȃ�
						}else{
							//SQL�p�ɃV���O���N�H�[�e�[�V�����ǉ�
							shisanHi = "'" + shisanHi + "'";
						}
					}
				}else{
					if(shisanHi.equals("null")){
						//�������Ȃ�
					}else{
						//SQL�p�ɃV���O���N�H�[�e�[�V�����ǉ�
						shisanHi = "'" + shisanHi + "'";
					}
				}
				//�yH24�N�x�Ή��zNo.11 End


				//�J���}�폜
				String yuuko_budomari = toString(reqData.getFieldVale("keisan", i, "yuuko_budomari"),"",",");
				String heikinjyutenryo = toString(reqData.getFieldVale("keisan", i, "heikinjyutenryo"),"",",");
				String kesu_kotehi = toString(reqData.getFieldVale("keisan", i, "kesu_kotehi"),"",",");
				String kg_kotehi = toString(reqData.getFieldVale("keisan", i, "kg_kotehi"),"",",");
				// ADD 2013/11/1 QP@30154 okano start
				String kesu_rieki = toString(reqData.getFieldVale("keisan", i, "kesu_rieki"),"",",");
				String kg_rieki = toString(reqData.getFieldVale("keisan", i, "kg_rieki"),"",",");
				// ADD 2013/11/1 QP@30154 okano end
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
				String fg_koumokuchk = toString(reqData.getFieldVale("keisan", i, "fg_koumokuchk"),"",",");
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N

				strSQL = new StringBuffer();

				strSQL.append("UPDATE tr_shisan_shisaku");
				strSQL.append("   SET ");
				strSQL.append("      budomari = " + toString(yuuko_budomari,"null"));
				strSQL.append("      ,heikinjuten = " + toString(heikinjyutenryo,"null"));
				strSQL.append("      ,cs_kotei = " + toString(kesu_kotehi,"null"));
				strSQL.append("      ,kg_kotei = " + toString(kg_kotehi,"null"));
				// ADD 2013/11/1 QP@30154 okano start
				strSQL.append("      ,cs_rieki = " + toString(kesu_rieki,"null"));
				strSQL.append("      ,kg_rieki = " + toString(kg_rieki,"null"));
				// ADD 2013/11/1 QP@30154 okano end
				strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
				strSQL.append("      ,dt_koshin = GETDATE()");

				//�yQP@00342�z���Z���~
				if(fg_chusi.equals("1")){
					//���Z���~�̏ꍇ�Ɏ��Z���~�t���O���X�V
					strSQL.append("      ,fg_chusi = " + fg_chusi);
				}
				else{
					//���Z���~�łȂ��ꍇ�Ɏ��Z���t���X�V
					strSQL.append("      ,dt_shisan = " + shisanHi);
				}
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N
				if(fg_koumokuchk.equals("1")){
					strSQL.append("      ,fg_koumokuchk = 1 ");
				}else{
					strSQL.append("      ,fg_koumokuchk = 0 ");
				}
//ADD 2013/07/11 ogawa �yQP@30151�zNo.13 start    //���ڌŒ�`�F�b�N

				strSQL.append(" WHERE");
				strSQL.append("	cd_shain = "    + strReqShainCd);
				strSQL.append("   AND nen = "   + strReqNen);
				strSQL.append("   AND no_oi = " + strReqOiNo);

				//�yQP@00342�z
				strSQL.append("  AND no_eda = " + strReqEda );

				strSQL.append("   AND seq_shisaku = " + reqData.getFieldVale("keisan", i, "seq_shisaku"));

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̍폜���s���B
				this.execDB.execSQL(strSQL.toString());
			}

//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start    //���ڌŒ�\���p��DB�X�V�iDELETE��INSERT�j
			//�X�V�pSQL�쐬(�Œ荀�ڂ�ON,OFF�Ɋւ�炸DB�X�V)
			for ( int i=0; i<reqData.getCntRow("keisan"); i++ ) {

				//���K�\���F���l�m�F�p
				Pattern pattern = Pattern.compile("^[-]?[0-9]*[.]?[0-9]+");

				//���N�G�X�g�f�[�^��莎��R�[�h�擾
				strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
				strReqNen = reqData.getFieldVale("kihon", 0, "nen");
				strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
				strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");
				String strReqSeq = reqData.getFieldVale("keisan", i, "seq_shisaku");

				//�v�Z�Œ荀�ڎ擾
				String strZyusui = toString(reqData.getFieldVale("keisan", i, "zyusui"),"",",");
				String strZyuabura = toString(reqData.getFieldVale("keisan", i, "zyuabura"),"",",");
				String strGokei = toString(reqData.getFieldVale("keisan", i, "gokei"),"",",");
				String strHiju = toString(reqData.getFieldVale("keisan", i, "hiju"),"",",");
				String strReberu = toString(reqData.getFieldVale("keisan", i, "reberu"),"",",");
				String strHijukasan = toString(reqData.getFieldVale("keisan", i, "hijukasan"),"",",");
				String strCsgenryo = toString(reqData.getFieldVale("keisan", i, "cs_genryo"),"",",");
				String strCszairyohi = toString(reqData.getFieldVale("keisan", i, "cs_zairyohi"),"",",");
				String strCsgenka = toString(reqData.getFieldVale("keisan", i, "cs_genka"),"",",");
				String strKogenka = toString(reqData.getFieldVale("keisan", i, "ko_genka"),"",",");
				String strKggenryo = toString(reqData.getFieldVale("keisan", i, "kg_genryo"),"",",");
				String strKgzairyohi = toString(reqData.getFieldVale("keisan", i, "kg_zairyohi"),"",",");
				String strKggenka = toString(reqData.getFieldVale("keisan", i, "kg_genka"),"",",");
				String strBaika = toString(reqData.getFieldVale("keisan", i, "baika"),"");
				String strArari = toString(reqData.getFieldVale("keisan", i, "arari"),"",",");

				//�e���̒P�ʁi���j�폜
				//�������̒P�ʂ͍��ڌŒ莞�̂��̂�ۑ�����ׁA�����ł̍폜�͂��Ȃ�
				Matcher matcher = pattern.matcher(strArari);
				if (matcher.find()) {
					strArari = matcher.group();
				} else {
					strArari = "";
				}

				//DELETE���쐬
				strSQL = new StringBuffer();
				strSQL.append(" DELETE FROM");
				strSQL.append("    tr_shisan_shisaku_kotei");
				strSQL.append(" WHERE");
				strSQL.append("    cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				this.execDB.execSQL(strSQL.toString());

				//INSERT���쐬
				strSQL = new StringBuffer();
				strSQL.append(" INSERT INTO tr_shisan_shisaku_kotei ");
				strSQL.append("            ( cd_shain ");
				strSQL.append("            , nen ");
				strSQL.append("            , no_oi ");
				strSQL.append("            , seq_shisaku ");
				strSQL.append("            , no_eda ");
				strSQL.append("            , zyusui ");
				strSQL.append("            , zyuabura ");
				strSQL.append("            , gokei ");
				strSQL.append("            , hiju ");
				strSQL.append("            , reberu ");
				strSQL.append("            , hijukasan ");
				strSQL.append("            , cs_genryo ");
				strSQL.append("            , cs_zairyohi ");
				strSQL.append("            , cs_genka ");
				strSQL.append("            , ko_genka ");
				strSQL.append("            , kg_genryo ");
				strSQL.append("            , kg_zairyohi ");
				strSQL.append("            , kg_genka ");
				strSQL.append("            , baika ");
				strSQL.append("            , arari ");
				strSQL.append("            , id_toroku ");
				strSQL.append("            , dt_toroku ");
				strSQL.append("            , id_koshin ");
				strSQL.append("            , dt_koshin )");
				strSQL.append("      VALUES");
				strSQL.append("            (" + strReqShainCd);
				strSQL.append("            ," + strReqNen);
				strSQL.append("            ," + strReqOiNo);
				strSQL.append("            ," + strReqSeq);
				strSQL.append("            ," + strReqEda);
				strSQL.append("            ,'" + strZyusui + "'");
				strSQL.append("            ,'" + strZyuabura + "'");
				strSQL.append("            ,'" + strGokei + "'");
				strSQL.append("            ,'" + strHiju + "'");
				strSQL.append("            ,'" + strReberu + "'");
				strSQL.append("            ,'" + strHijukasan + "'");
				strSQL.append("            ,'" + strCsgenryo + "'");
				strSQL.append("            ,'" + strCszairyohi + "'");
				strSQL.append("            ,'" + strCsgenka + "'");
				strSQL.append("            ,'" + strKogenka + "'");
				strSQL.append("            ,'" + strKggenryo + "'");
				strSQL.append("            ,'" + strKgzairyohi + "'");
				strSQL.append("            ,'" + strKggenka + "'");
				strSQL.append("            ,'" + strBaika + "'");
				strSQL.append("            ,'" + strArari + "'");
				strSQL.append("            ," + userInfoData.getId_user());
				strSQL.append("            ,GETDATE()");
				strSQL.append("            ," + userInfoData.getId_user());
				strSQL.append("            ,GETDATE())");

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				this.execDB.execSQL(strSQL.toString());
			}
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end    //���ڌŒ�\���p��DB�X�V�iDELETE��INSERT�j

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�o�^[keisan]���̍X�VSQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}
	}

	/**
	 * �������Z�o�^[shizai]���̍폜����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuShizaiDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//�yQP@00342�z
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");


			//�폜�pSQL�쐬
			strSQL.append(" DELETE FROM tr_shisan_shizai");
			strSQL.append(" WHERE");
			strSQL.append("	cd_shain = "    + strReqShainCd);
			strSQL.append("   AND nen = "   + strReqNen);
			strSQL.append("   AND no_oi = " + strReqOiNo);

			//�yQP@00342�z
			strSQL.append("   AND no_eda = " + strReqEda);


			//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�o�^[shizai]���̍폜SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}
	}

	/**
	 * �������Z�o�^[shizai]���̓o�^����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuShizaiInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//�yQP@00342�z
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");


			//�o�^�pSQL�쐬
			for ( int i=0; i<reqData.getCntRow("shizai"); i++ ) {

				strSQL = new StringBuffer();

				//����SEQ���Ȃ��ꍇ
				if(toString(reqData.getFieldVale("shizai", i, "seq_shizai")).equals("")){
					//�������Ȃ�

				}else{

					//�J���}�폜
					String tanka = toString(reqData.getFieldVale("shizai", i, "tanka"),"",",");
					String budomari = toString(reqData.getFieldVale("shizai", i, "budomari"),"",",");
					String shiyouryo = toString(reqData.getFieldVale("shizai", i, "shiyouryo"),"",",");

					strSQL.append("INSERT INTO tr_shisan_shizai");
					strSQL.append("   (cd_shain");
					strSQL.append("   ,nen");
					strSQL.append("   ,no_oi");
					strSQL.append("   ,no_eda");
					strSQL.append("   ,seq_shizai");
					strSQL.append("   ,no_sort");
					strSQL.append("   ,cd_kaisha");
					strSQL.append("   ,cd_busho");
					strSQL.append("   ,cd_shizai");
					strSQL.append("   ,nm_shizai");
					strSQL.append("   ,tanka");
					strSQL.append("   ,budomari");
					strSQL.append("  ,cs_siyou");
					strSQL.append("   ,id_toroku");
					strSQL.append("   ,dt_toroku");
					strSQL.append("   ,id_koshin");
					strSQL.append("  ,dt_koshin)");
					strSQL.append(" VALUES");
					strSQL.append("   (" + strReqShainCd );
					strSQL.append("   ," + strReqNen );
					strSQL.append("   ," + strReqOiNo);

					//�yQP@00342�z
					strSQL.append("   ," + strReqEda);

					strSQL.append("   ," + reqData.getFieldVale("shizai", i, "seq_shizai"));
					strSQL.append("   ," + reqData.getFieldVale("shizai", i, "seq_shizai"));
					strSQL.append("   ," + toString(reqData.getFieldVale("shizai", i, "cd_kaisya"),"null", ","));
					strSQL.append("   ," + toString(reqData.getFieldVale("shizai", i, "cd_kojyo"),"null", ","));
					strSQL.append("   ," + toString(reqData.getFieldVale("shizai", i, "cd_shizai"),"null", ","));
					strSQL.append("   ,'" + toString(reqData.getFieldVale("shizai", i, "nm_shizai"),"") + "'");
					strSQL.append("   ," + toString(tanka,"null", ","));
					strSQL.append("   ," + toString(budomari,"null", ","));
					strSQL.append("   ," + toString(shiyouryo,"null", ","));

					//�o�^��ID�A�o�^��
					if(toString(reqData.getFieldVale("shizai", i, "id_toroku")).equals("")){
						strSQL.append("   ," + userInfoData.getId_user());
						strSQL.append("   ,GETDATE()");

					}else{
						strSQL.append("   ,'" + toString(reqData.getFieldVale("shizai", i, "id_toroku")) + "'");
						strSQL.append("   ,'" + toString(reqData.getFieldVale("shizai", i, "dt_toroku")) + "'");

					}

					strSQL.append("   ," + userInfoData.getId_user());
					strSQL.append("   ,GETDATE() )");

					//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̍폜���s���B
					this.execDB.execSQL(strSQL.toString());
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�o�^[shizai]���̓o�^SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

		}
	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData�i�p�����[�^�[�i�[�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : ����e�[�u���f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
//�C���O�\�[�X
//	private void storageGenkaTorokuDataKanri(RequestResponsTableBean resTable)
//�C����\�[�X
	private void storageGenkaTorokuDataKanri(RequestResponsTableBean resTable, String msgcd)
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 end
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
//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 start
			resTable.addFieldVale(0, "msg_cd", msgcd);
//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 end

		} catch (Exception e) {
			this.em.ThrowException(e, "�Ǘ����ʃp�����[�^�[�i�[���������s���܂����B");

		} finally {

		}
	}



	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	/**
	 * �������Z�o�^[kihonsub]���̍X�V����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuKihonSubUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {
			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//�yQP@00342�z
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

			//�X�V����
			for ( int i=0; i<reqData.getCntRow("kihonsub"); i++ ) {

				strSQL.append(" UPDATE tr_shisan_kihonjoho");
				strSQL.append("   SET ");
				strSQL.append("       lot = '"      + toString( reqData.getFieldVale( "kihonsub", i, "seizo_roto"),"") + "'");
				strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
				strSQL.append("     ,dt_koshin = GETDATE()");

				strSQL.append(" WHERE ");
				strSQL.append("       cd_shain = " + strReqShainCd);
				strSQL.append("   AND nen = "   + strReqNen);
				strSQL.append("   AND no_oi = " + strReqOiNo);
				strSQL.append("   AND no_eda = " + strReqEda );
				strSQL.append("   AND seq_shisaku = " + reqData.getFieldVale("kihonsub", i, "seq_shisaku"));

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̍폜���s���B
				this.execDB.execSQL(strSQL.toString());

			}
		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�o�^[kihonsub]���̍X�VSQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL = null;

			//�Z�b�V�����̃N���[�Y
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

		}
	}
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

}