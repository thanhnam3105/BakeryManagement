package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * �������Z�i�c�Ɓj�f�[�^���Ǘ��i�o�^�E�X�V�j����SQL���쐬����B
 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FFGEN2160O�v�ɐݒ肷��B
 *
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class FGEN2160_Logic extends LogicBase {

	/**
	 * �������Z�i�c�Ɓj�Ǘ��R���X�g���N�^ �F �C���X�^���X����
	 */
	public FGEN2160_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �������Z�i�c�Ɓj�f�[�^����Ǘ� �F �������Z�i�c�Ɓj�f�[�^�̑���Ǘ����s���B
	 *
	 * @param reqData
	 *            : ���N�G�X�g�f�[�^
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

		StringBuffer strSql = new StringBuffer();

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			String st_setting = null;
//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 start
			String dsp_data = null;
//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 end

			// �g�����U�N�V�����J�n
			super.createExecDB();
			execDB.BeginTran();

			// �X�e�[�^�X�ݒ�̎擾
			st_setting = reqData.getFieldVale(0, 0, "st_setting");

			// ����DB�X�V�i���Z�@����i�j
			strSql = statusKyotuUpdateSQL_sisan_sisakuhin(reqData);
			execDB.execSQL(strSql.toString()); // SQL�����s

			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			// �T���v����DB�X�V(����@��{���)
			this.statusKihonSubUpdateSQL_sisan_kihonjoho(reqData);
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

			// ����DB�X�V�i���Z�����i�c�ƘA���j�j
			strSql = statusKyotuUpdateSQL_eigyo_memo(reqData);
			execDB.execSQL(strSql.toString()); // SQL�����s

			//�c�Ƃ̌��݃X�e�[�^�X�擾
			int st_eigyo = Integer.parseInt(reqData.getFieldVale(0, 0, "st_eigyo"));

			// �X�e�[�^�X�ݒ�F���ۑ�
			if (st_setting.equals("0")) {

				//�X�e�[�^�X����}��
				strSql = statusRirekiInsertSQL(reqData , st_eigyo , "0");
				execDB.execSQL(strSql.toString()); // SQL�����s
				//ADD 2015/07/28 TT.Kitazawa�yQP@40812�zNo.6 start
				dsp_data = "RGEN2161";
				//ADD 2015/07/28 TT.Kitazawa�yQP@40812�zNo.6 start
			}
			// �X�e�[�^�X�ݒ�F���Z�˗�
			else if (st_setting.equals("1")) {
				//�X�e�[�^�X�e�[�u���X�V
				strSql = statusUpdateSQL(reqData , 2);
				execDB.execSQL(strSql.toString()); // SQL�����s

				//�X�e�[�^�X����}��
				strSql = statusRirekiInsertSQL(reqData , 2 , "1");
				execDB.execSQL(strSql.toString()); // SQL�����s

//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 start
				dsp_data = "RGEN2162";
//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 end
			}
			// �X�e�[�^�X�ݒ�F�m�F����
			else if (st_setting.equals("2")) {
				//�X�e�[�^�X�e�[�u���X�V
				strSql = statusUpdateSQL(reqData , 3);
				execDB.execSQL(strSql.toString()); // SQL�����s

				//�X�e�[�^�X����}��
				strSql = statusRirekiInsertSQL(reqData , 3 , "2");
				execDB.execSQL(strSql.toString()); // SQL�����s

//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 start
				dsp_data = "RGEN2163";
//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 end

//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				strSql = koteikomokuUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

			}
			// �X�e�[�^�X�ݒ�F�̗p�L���m��i�̗p�L��j
			else if (st_setting.equals("3")) {
				//�X�e�[�^�X�e�[�u���X�V
				strSql = statusUpdateSQL(reqData , 4);
				execDB.execSQL(strSql.toString()); // SQL�����s

				//�X�e�[�^�X����}��
				if(st_eigyo == 2){
					//���Z�˗�����̗̍p�L���m��̏ꍇ�́A�u�m�F�����v�X�e�[�^�X�𗚗��ɒǉ�
					strSql = statusRirekiInsertSQL(reqData , 3 , "2");
					execDB.execSQL(strSql.toString()); // SQL�����s
				}
				Thread.sleep(1100);
				strSql = statusRirekiInsertSQL(reqData , 4 , "3");
				execDB.execSQL(strSql.toString()); // SQL�����s

				//�̗p�����No�X�V
				String saiyou = toString(reqData.getFieldVale(0, 0, "no_saiyou"));
				strSql = saiyouSampleUpdateSQL(reqData, saiyou);
				execDB.execSQL(strSql.toString()); // SQL�����s

//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				strSql = koteikomokuUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

			}
			// �X�e�[�^�X�ݒ�F�̗p�L���m��i�̗p�����j
			else if (st_setting.equals("4")) {
				//�X�e�[�^�X�e�[�u���X�V
				strSql = statusUpdateSQL(reqData , 4);
				execDB.execSQL(strSql.toString()); // SQL�����s

				//�X�e�[�^�X����}��
				if(st_eigyo == 2){
					//���Z�˗�����̗̍p�L���m��̏ꍇ�́A�u�m�F�����v�X�e�[�^�X�𗚗��ɒǉ�
					strSql = statusRirekiInsertSQL(reqData , 3 , "2");
					execDB.execSQL(strSql.toString()); // SQL�����s
				}
				Thread.sleep(1100);
				strSql = statusRirekiInsertSQL(reqData , 4 , "4");
				execDB.execSQL(strSql.toString()); // SQL�����s

				//�̗p�����No�X�V
				strSql = saiyouSampleUpdateSQL(reqData, "-1");
				execDB.execSQL(strSql.toString()); // SQL�����s

//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				strSql = koteikomokuUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
			}
			// �X�e�[�^�X�ݒ�F���Z�r���ł̕s�̗p����
			else if (st_setting.equals("5")) {
				//�X�e�[�^�X�e�[�u���X�V
				strSql = statusUpdateSQL(reqData , 4);
				execDB.execSQL(strSql.toString()); // SQL�����s

				//�X�e�[�^�X����}��
				strSql = statusRirekiInsertSQL(reqData , 4 , "5");
				execDB.execSQL(strSql.toString()); // SQL�����s

				//�̗p�����No�X�V
				strSql = saiyouSampleUpdateSQL(reqData, "-1");
				execDB.execSQL(strSql.toString()); // SQL�����s

//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				strSql = koteikomokuUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
			}

			// �R�~�b�g
			execDB.Commit();

			// �@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// �e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̌`��
//MOD 2013/06/28 ogawa �yQP@30151�zNo.38 start
//�C���O�\�[�X
//			storageGroupDataKanri(resKind.getTableItem(strTableNm));
//�C����\�[�X
			storageGroupDataKanri(resKind.getTableItem(strTableNm), dsp_data);
//MOD 2013/06/28 ogawa �yQP@30151�zNo.38 end

		} catch (Exception e) {
			if (execDB != null) {
				// ���[���o�b�N
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");
		} finally {
			if (execDB != null) {
				// �Z�b�V�����̃N���[�Y
				execDB.Close();
				execDB = null;
			}

			//�ϐ��̍폜
			strSql = null;
		}
		return resKind;
	}

	/**
	 * �S�X�e�[�^�X����SQL�쐬�i���Z�@����i�e�[�u���j
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer statusKyotuUpdateSQL_sisan_sisakuhin(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			String dt_kizitu = toString( requestData.getFieldVale("kihon", 0, "dt_kizitu"));
			String yoryo = toString( requestData.getFieldVale("kihon", 0, "yoryo"), "", ",");
			String su_iri = toString( requestData.getFieldVale("kihon", 0, "su_iri"), "", ",");
			String nisugata = toString( requestData.getFieldVale("kihon", 0, "nisugata"));
			// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
//			String genka = toString( requestData.getFieldVale("kihon", 0, "genka"), "", ",");
//			String genka_tani = toString( requestData.getFieldVale("kihon", 0, "genka_tani"));
//			String baika = toString( requestData.getFieldVale("kihon", 0, "baika"), "", ",");
//			String sotei_buturyo = toString( requestData.getFieldVale("kihon", 0, "sotei_buturyo"));
//			String dt_hatubai = toString( requestData.getFieldVale("kihon", 0, "dt_hatubai"));
//			String hanbai_t = toString( requestData.getFieldVale("kihon", 0, "hanbai_t"));
//			String hanbai_s = toString( requestData.getFieldVale("kihon", 0, "hanabai_s"));
//			String hanabai_k = toString( requestData.getFieldVale("kihon", 0, "hanabai_k"));
//			String keikakuuriage = toString( requestData.getFieldVale("kihon", 0, "keikakuuriage"));
//			String keikakurieki = toString( requestData.getFieldVale("kihon", 0, "keikakurieki"));
//			String kuhaku_1 = toString( requestData.getFieldVale("kihon", 0, "kuhaku_1"));
//			String kuhaku_2 = toString( requestData.getFieldVale("kihon", 0, "kuhaku_2"));
			// DEL 2013/7/2 shima�yQP@30151�zNo.37 end

			if(dt_kizitu.equals("")){
				dt_kizitu = "NULL";
			}
			else{
				dt_kizitu = "'" + dt_kizitu + "'";
			}

			// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
//			if(hanbai_s.equals("")){
//				hanbai_s = "NULL";
//			}
			// DEL 2013/7/2 shima�yQP@30151�zNo.37 end

			// SQL���̍쐬
			strInsSql.append(" UPDATE tr_shisan_shisakuhin ");
			strInsSql.append("    SET  ");
			strInsSql.append("        dt_kizitu = " + dt_kizitu + " ");
			strInsSql.append("       ,yoryo = '" + yoryo + "' ");
			strInsSql.append("       ,su_iri = '" + su_iri + "' ");
			strInsSql.append("       ,cd_nisugata = '" + nisugata + "' ");
			// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
//			strInsSql.append("       ,genka = '" + genka + "' ");
//			strInsSql.append("       ,baika = '" + baika + "' ");
//			strInsSql.append("       ,cd_genka_tani = '" + genka_tani + "' ");
//			strInsSql.append("       ,buturyo = '" + sotei_buturyo + "' ");
//			strInsSql.append("       ,dt_hatubai = '" + dt_hatubai + "' ");
//			strInsSql.append("       ,kikan_hanbai_sen = '" + hanbai_t + "' ");
//			strInsSql.append("       ,kikan_hanbai_suti = " + hanbai_s + " ");
//			strInsSql.append("       ,kikan_hanbai_tani = '" + hanabai_k + "' ");
//			strInsSql.append("       ,uriage_k = '" + keikakuuriage + "' ");
//			strInsSql.append("       ,rieki_k = '" + keikakurieki + "' ");
//			strInsSql.append("       ,uriage_h = '" + kuhaku_1 + "' ");
//			strInsSql.append("       ,rieki_h = '" + kuhaku_2 + "' ");
			// DEL 2013/7/2 shima�yQP@30151�zNo.37 end
			strInsSql.append("       ,id_toroku =  " + userInfoData.getId_user());
			strInsSql.append("       ,dt_toroku = GETDATE() ");
			strInsSql.append("  WHERE  ");
			strInsSql.append(" 	  cd_shain =  " + cd_shain);
			strInsSql.append("       AND nen =  " + nen);
			strInsSql.append("       AND no_oi =  " + no_oi);
			strInsSql.append("       AND no_eda =  " + no_eda);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	/**
	 * �S�X�e�[�^�X����SQL�쐬�i���Z�@��{���T�u�e�[�u���j
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void statusKihonSubUpdateSQL_sisan_kihonjoho(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

			for(int i = 0;i < requestData.getCntRow("kihonsub"); i++){

				String genka = toString( requestData.getFieldVale("kihonsub", i, "genka"), "", ",");
				String genka_tani = toString( requestData.getFieldVale("kihonsub", i, "genka_tani"));
				String baika = toString( requestData.getFieldVale("kihonsub", i, "baika"), "", ",");
				// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
				// MOD 2013/12/4 okano�yQP@30154�zstart
//					String sotei_buturyo_s = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo_s"));
				String sotei_buturyo_s = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo_s"), "", ",");
				// MOD 2013/12/4 okano�yQP@30154�zend
				String sotei_buturyo_u = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo_u"));
				String sotei_buturyo_k = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo_k"));
				// ADD 2013/9/6 okano�yQP@30151�zNo.30 end
				String sotei_buturyo = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo"));
				String dt_hatubai = toString( requestData.getFieldVale("kihonsub", i, "dt_hatubai"));
				String hanbai_t = toString( requestData.getFieldVale("kihonsub", i, "hanbai_t"));
				String hanbai_s = toString( requestData.getFieldVale("kihonsub", i, "hanabai_s"));
				String hanabai_k = toString( requestData.getFieldVale("kihonsub", i, "hanabai_k"));
				String keikakuuriage = toString( requestData.getFieldVale("kihonsub", i, "keikakuuriage"));
				String keikakurieki = toString( requestData.getFieldVale("kihonsub", i, "keikakurieki"));
				String kuhaku_1 = toString( requestData.getFieldVale("kihonsub", i, "kuhaku_1"));
				String kuhaku_2 = toString( requestData.getFieldVale("kihonsub", i, "kuhaku_2"));


				if(hanbai_s.equals("")){
					hanbai_s = "NULL";
				}

				// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
				if(sotei_buturyo_s.equals("")){
					sotei_buturyo_s = "NULL";
				}
				// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

				// SQL���̍쐬
				strInsSql.append(" UPDATE tr_shisan_kihonjoho ");
				strInsSql.append("    SET  ");
				strInsSql.append("       genka = '" + genka + "' ");
				strInsSql.append("       ,baika = '" + baika + "' ");
				strInsSql.append("       ,cd_genka_tani = '" + genka_tani + "' ");
				// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
				strInsSql.append("       ,buturyo_suti = " + sotei_buturyo_s + " ");
				strInsSql.append("       ,buturyo_seihin = '" + sotei_buturyo_u + "' ");
				strInsSql.append("       ,buturyo_kikan = '" + sotei_buturyo_k + "' ");
				// ADD 2013/9/6 okano�yQP@30151�zNo.30 end
				strInsSql.append("       ,buturyo = '" + sotei_buturyo + "' ");
				strInsSql.append("       ,dt_hatubai = '" + dt_hatubai + "' ");
				strInsSql.append("       ,kikan_hanbai_sen = '" + hanbai_t + "' ");
				strInsSql.append("       ,kikan_hanbai_suti = " + hanbai_s + " ");
				strInsSql.append("       ,kikan_hanbai_tani = '" + hanabai_k + "' ");
				strInsSql.append("       ,uriage_k = '" + keikakuuriage + "' ");
				strInsSql.append("       ,rieki_k = '" + keikakurieki + "' ");
				strInsSql.append("       ,uriage_h = '" + kuhaku_1 + "' ");
				strInsSql.append("       ,rieki_h = '" + kuhaku_2 + "' ");
				strInsSql.append("       ,id_koshin =  " + userInfoData.getId_user());
				strInsSql.append("       ,dt_koshin = GETDATE() ");
				strInsSql.append("  WHERE  ");
				strInsSql.append(" 	  cd_shain =  " + cd_shain);
				strInsSql.append("       AND nen =  " + nen);
				strInsSql.append("       AND no_oi =  " + no_oi);
				strInsSql.append("       AND no_eda =  " + no_eda);
				strInsSql.append("       AND seq_shisaku = " + toString( requestData.getFieldVale("kihonsub", i, "seq_shisaku")));

				this.execDB.execSQL(strInsSql.toString());
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�o�^[kihonsub]���̍X�VSQL�쐬���������s���܂����B");
		} finally {
			//�ϐ��̍폜
			strInsSql = null;

			//�Z�b�V�����̃N���[�Y
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

		}
	}
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

	/**
	 * �S�X�e�[�^�X����SQL�쐬�i���Z�����i�c�ƘA���p�j�e�[�u���j
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer statusKyotuUpdateSQL_eigyo_memo(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String memo_eigyo = toString( requestData.getFieldVale("kihon", 0, "memo_eigyo"));

			// SQL���̍쐬
			strInsSql.append(" UPDATE tr_shisan_memo_eigyo ");
			strInsSql.append("    SET  ");
			strInsSql.append("       memo_eigyo = '" + memo_eigyo + "' ");
			strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
			strInsSql.append("       ,dt_koshin = GETDATE()");
			strInsSql.append("  WHERE ");
			strInsSql.append(" 	  cd_shain = " + cd_shain);
			strInsSql.append("       AND nen = " + nen);
			strInsSql.append("       AND no_oi = " + no_oi);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * �X�e�[�^�X�X�VSQL�쐬�i�X�e�[�^�X�e�[�u���j
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer statusUpdateSQL(
				RequestResponsKindBean requestData
				,int Status_Eigyo
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

			// SQL���̍쐬
			strInsSql.append(" UPDATE tr_shisan_status ");
			strInsSql.append("    SET  ");
			strInsSql.append("        st_eigyo = " + Status_Eigyo);
			strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
			strInsSql.append("       ,dt_koshin = GETDATE()");
			strInsSql.append("  WHERE ");
			strInsSql.append(" 	  cd_shain =  " + cd_shain);
			strInsSql.append("       AND nen =  " + nen);
			strInsSql.append("       AND no_oi =  " + no_oi);
			strInsSql.append("       AND no_eda =  " + no_eda);


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * �X�e�[�^�X�����X�VSQL�쐬�i�X�e�[�^�X�����e�[�u���j
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer statusRirekiInsertSQL(
				RequestResponsKindBean requestData
				,int Status_Eigyo
				,String cd_literal
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//���N�G�X�g�f�[�^�擾
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			String st_kenkyu = toString( requestData.getFieldVale("kihon", 0, "st_kenkyu"));
			String st_seisan = toString( requestData.getFieldVale("kihon", 0, "st_seikan"));
			String st_gensizai = toString( requestData.getFieldVale("kihon", 0, "st_gentyo"));
			String st_kojo = toString( requestData.getFieldVale("kihon", 0, "st_kojo"));


			// SQL���̍쐬
			strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
			strInsSql.append("            (cd_shain ");
			strInsSql.append("            ,nen ");
			strInsSql.append("            ,no_oi ");
			strInsSql.append("            ,no_eda ");
			strInsSql.append("            ,dt_henkou ");
			strInsSql.append("            ,cd_kaisha ");
			strInsSql.append("            ,cd_busho ");
			strInsSql.append("            ,id_henkou ");
			strInsSql.append("            ,cd_zikko_ca ");
			strInsSql.append("            ,cd_zikko_li ");
			strInsSql.append("            ,st_kenkyu ");
			strInsSql.append("            ,st_seisan ");
			strInsSql.append("            ,st_gensizai ");
			strInsSql.append("            ,st_kojo ");
			strInsSql.append("            ,st_eigyo ");
			strInsSql.append("            ,id_toroku ");
			strInsSql.append("            ,dt_toroku) ");
			strInsSql.append("      VALUES ");
			strInsSql.append("            (" + cd_shain + " ");
			strInsSql.append("            ," + nen + " ");
			strInsSql.append("            ," + no_oi + " ");
			strInsSql.append("            ," + no_eda +" ");
			strInsSql.append("            ,GETDATE() ");
			strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
			strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
			strInsSql.append("            ," + userInfoData.getId_user() + " ");
			strInsSql.append("            ,'wk_eigyo'");
			strInsSql.append("            ,'" + cd_literal +"' ");
			strInsSql.append("            ," + st_kenkyu + " ");
			strInsSql.append("            ," + st_seisan + " ");
			strInsSql.append("            ," + st_gensizai + " ");
			strInsSql.append("            ," + st_kojo + " ");
			strInsSql.append("            ," + Status_Eigyo + " ");
			strInsSql.append("            ," + userInfoData.getId_user() + " ");
			strInsSql.append("            ,GETDATE() )");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * �̗p�����No�X�VSQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer saiyouSampleUpdateSQL(
				RequestResponsKindBean requestData
				,String No_Saiyou
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

			// SQL���̍쐬
			strInsSql.append(" UPDATE tr_shisan_shisakuhin ");
			strInsSql.append("    SET  ");
			strInsSql.append("       saiyo_sample = " + No_Saiyou + " ");
			strInsSql.append("       ,id_koshin = " + userInfoData.getId_user() + " ");
			strInsSql.append("       ,dt_koshin = GETDATE() ");
			strInsSql.append("  WHERE  ");
			strInsSql.append(" 	  cd_shain =  " + cd_shain);
			strInsSql.append("       AND nen =  " + nen);
			strInsSql.append("       AND no_oi =  " + no_oi);
			strInsSql.append("       AND no_eda =  " + no_eda);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : �S���҃f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
//MOD 2013/06/28 ogawa �yQP@30151�zNo.38 start
//�C���O�\�[�X
//	private void storageGroupDataKanri(RequestResponsTableBean resTableo)
//�C����\�[�X
	private void storageGroupDataKanri(RequestResponsTableBean resTable, String msgno)
//MOD 2013/06/28 ogawa �yQP@30151�zNo.38 end
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 start
			resTable.addFieldVale(0, "msg_cd", msgno);
//ADD 2013/06/28 ogawa �yQP@30151�zNo.38 end

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11
	 * �Œ荀�ڈꊇ�X�VSQL�i�X�e�[�^�X�e�[�u���j
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer koteikomokuUpdateSQL(
				RequestResponsKindBean requestData
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

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

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

}


