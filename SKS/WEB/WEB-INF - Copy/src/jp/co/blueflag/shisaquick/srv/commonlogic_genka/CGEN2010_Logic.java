package jp.co.blueflag.shisaquick.srv.commonlogic_genka;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * �������Z�˗��f�[�^�����Zdb�ɃR�s�[�����/�H��̐􂢑ւ����s��
 *  : ���얈�̐􂢑ւ�
 * @author isono
 * @since  2009/10/22
 *
 */
public class CGEN2010_Logic extends LogicBase {

	//�N���X�����o�ϐ�

	//�􂢑ւ��p�@���CD
	int cls_cd_kaisha = 0;
	//�􂢑ւ��p�@�H��CD
	int cls_cd_kojo = 0;

	/**
	 * �R���X�g���N�^
	 */
	public CGEN2010_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �������Z�˗��f�[�^��copy�̎���
	 * �F�������Z�pcopy
	 * �@���/�H��􂢑ւ�
	 * @param cd_shain	�F����CD�@�Ј�ID
	 * @param nen		�F����CD	�N
	 * @param no_oi		�F����CD�@�ǔ�
	 * @param no_eda	�F�yQP@00342�z�}��
	 * @param list_seq_shisaku	�F�˗��̗L��������ق̎���SEQ���X�g
	 * @param userInfo	�F���[�U�[���
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void ExecLogic(

			BigDecimal             	cd_shain
			,int                nen
			,int                no_oi
			,int				 no_eda
			,ArrayList<Integer> list_seq_shisaku
			,UserInfoData       userInfo

			)

	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = userInfo;
		//SQL�o�b�t�@
		StringBuffer strSQL = null;
		//�������ʃo�b�t�@
		List<?> lstSearchAry = null;
		//�yQP@00342�z�������ʃo�b�t�@�}��
		List<?> lstSearchAry_eda = null;
		//�􂢑ւ����sFg
		boolean blnAraigae = false;


		//�@�������Z�˗��̗L�����A����ق��������Z�ɺ�߰����B

		try{

			//DB�R�l�N�V��������
			super.createExecDB();
			super.createSearchDB();

			//�g�����U�N�V�����J�n
			this.searchDB.BeginTran();
			execDB.setSession(searchDB.getSession());


			//�yQP@00342�z
			//�}�Ԍ���SQL�쐬
			strSQL = null;
			//����SQL����
			strSQL = makeSearchSQL_Eda(cd_shain, nen, no_oi);
			//DB����
			lstSearchAry_eda = this.searchDB.dbSearch_notError(strSQL.toString());


			//���Z�@����i�e�[�u���iT310�j�L���m�F�i���/�H���������Ă���j
			//����SQL����
			strSQL = makeSearchSQL_T310(cd_shain, nen, no_oi);
			//DB����
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

			if ( lstSearchAry.size() > 0 ) {

				Object[] items = (Object[]) lstSearchAry.get(0);

				//T310�����݂��Ȃ��ꍇ
				if(toString(items[0]).equals("")){

					//���Z�@����i�e�[�u���iT310�j�ǉ�
					//�X�VSQL����
					strSQL = makeExecSQL_T310(cd_shain, nen, no_oi);
					//DB�X�V
					this.execDB.execSQL(strSQL.toString());

					//T110�̉��CD�A�H��CD���i�[
					//���CD�̑ޔ�
					cls_cd_kaisha = Integer.valueOf(toString(items[3])).intValue() ;
					//�H��CD�̑ޔ�
					cls_cd_kojo = Integer.valueOf(toString(items[4])).intValue() ;

					//�������Z�����e�[�u���쐬
					strSQL = null;
					strSQL = makeExecSQL_T311(cd_shain, nen, no_oi);
					//DB�X�V
					this.execDB.execSQL(strSQL.toString());

					//�������Z�����i�c�ƘA���p�j�e�[�u���쐬
					strSQL = null;
					strSQL = makeExecSQL_T312(cd_shain, nen, no_oi);
					//DB�X�V
					this.execDB.execSQL(strSQL.toString());

				}
				else{
					//�}�Ԑ����[�v
					for(int j = 0; j < lstSearchAry_eda.size(); j++){

						//�}�Ԏ擾
						Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
						String roop_no_eda = toString(items_eda[3]);

						//�c�ƃX�e�[�^�X�擾
						int st_eigyo = Integer.parseInt(toString(items_eda[6]));

						//�c�ƃX�e�[�^�X��4�����̎���ɒǉ�
						if(st_eigyo < 4 ){

							//���Z�@����i�e�[�u���iT310�j�˗��񐔍X�V
							//�X�VSQL����
							strSQL = makeExecSQL_sisanShisakuhin(cd_shain, nen, no_oi,roop_no_eda);
							//DB�X�V
							this.execDB.execSQL(strSQL.toString());
						}
					}
				}

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Ď��Z�˗����Ɏ��Z�����N���A
				strSQL = null;
				//�X�VSQL����
				strSQL = makeExecSQL_DtShisanClear(cd_shain, nen, no_oi);
				//DB�X�V
				this.execDB.execSQL(strSQL.toString());
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

			}else{

			}


			//�yQP@00342�z
			//���Z�@����e�[�u���փf�[�^�o�^ --------------------------------------------------------
			//�˗��̂��鎎��SEQ���[�v
			for (int i = 0; i < list_seq_shisaku.size(); i++) {

				//�}�Ԃ�����ꍇ
				if( lstSearchAry_eda.size() > 0 ){

					//���ŁA�}�ł֎����ǉ�

					//�}�Ԑ����[�v
					for(int j = 0; j < lstSearchAry_eda.size(); j++){

						//�}�Ԏ擾
						Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
						String roop_no_eda = toString(items_eda[3]);

						//�c�ƃX�e�[�^�X�擾
						int st_eigyo = Integer.parseInt(toString(items_eda[6]));

						//�c�ƃX�e�[�^�X��4�����̎���ɒǉ�
						if(st_eigyo < 4 ){

							// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
							//���Z�@��{���e�[�u���iT313�j�ǉ�
							strSQL = makeExecSQL_T313(cd_shain, nen, no_oi, list_seq_shisaku.get(i),roop_no_eda);
							//DB�X�V
							this.execDB.execSQL(strSQL.toString());
							// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

							//���Z�@����e�[�u���iT331�j�ǉ�
							//�X�VSQL����
							strSQL = makeExecSQL_T331(cd_shain, nen, no_oi, list_seq_shisaku.get(i),roop_no_eda);
							//DB�X�V
							this.execDB.execSQL(strSQL.toString());
							//�􂢑ւ�Fg���Z�b�g
							items_eda[7] = "true";
							//�􂢑ւ�Fg���Z�b�g
							blnAraigae = true;
						}
					}
				}
				//�}�Ԃ��Ȃ��ꍇ�i���Z�f�[�^���Ȃ��ꍇ�j
				else{

					//���ł֒ǉ�

					// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
					//���Z�@��{���e�[�u���iT313�j�ǉ�
					strSQL = makeExecSQL_T313(cd_shain, nen, no_oi, list_seq_shisaku.get(i), "0");
					//DB�X�V
					this.execDB.execSQL(strSQL.toString());

					strSQL = null;
					// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

					//���Z�@����e�[�u���iT331�j�ǉ�
					//�X�VSQL����
					strSQL = makeExecSQL_T331(cd_shain, nen, no_oi, list_seq_shisaku.get(i),"0");
					//DB�X�V
					this.execDB.execSQL(strSQL.toString());
					//�􂢑ւ�Fg���Z�b�g
					blnAraigae = true;

				}
			}

			//�yQP@00342�z
			//���Z�@�z���e�[�u���iT320�j�փf�[�^�o�^ --------------------------------------------------------
			//�}�Ԃ�����ꍇ
			if( lstSearchAry_eda.size() > 0 ){

				//���Z�@�z���e�[�u���iT320�j�L���m�F
				strSQL = null;
				//����SQL����
				strSQL = makeSearchSQL_T320(cd_shain, nen, no_oi);
				//DB����
				lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

				if ( lstSearchAry.size() >= 0 ) {

					for (int ix = 0; ix < lstSearchAry.size(); ix++) {

						strSQL = null;
						Object[] items = (Object[]) lstSearchAry.get(ix);

						if(toString(items[5]).equals("")){

							//���ŁA�}�ł֔z����ǉ�
							//�}�Ԑ����[�v
							for(int j = 0; j < lstSearchAry_eda.size(); j++){
								//�}�Ԏ擾
								Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
								String roop_no_eda = toString(items_eda[3]);

								//���Z�@�z���e�[�u���iT320�j�ǉ�
								//�X�VSQL����
								strSQL = makeExecSQL_T320(items,roop_no_eda);

								//DB�X�V
								this.execDB.execSQL(strSQL.toString());
							}
						}
					}
				}
			}
			else{
				//���ł֔z����ǉ�
				//���Z�@�z���e�[�u���iT320�j�L���m�F
				strSQL = null;
				//����SQL����
				strSQL = makeSearchSQL_T320(cd_shain, nen, no_oi);
				//DB����
				lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

				if ( lstSearchAry.size() >= 0 ) {

					for (int ix = 0; ix < lstSearchAry.size(); ix++) {

						strSQL = null;
						Object[] items = (Object[]) lstSearchAry.get(ix);

						if(toString(items[5]).equals("")){
							//���Z�@�z���e�[�u���iT320�j�ǉ�
							//�X�VSQL����
							strSQL = makeExecSQL_T320(items,"0");

							//DB�X�V
							this.execDB.execSQL(strSQL.toString());

						}
					}
				}
			}

			//�X�e�[�^�X�̓o�^�A�X�V
			//�􂢑ւ����sFg���Z�b�g����Ă���ꍇ�i�V�K�Ɏ��Z�˗������f�[�^������ꍇ�j
			if( blnAraigae ){

				//�}�Ԃ�����ꍇ�i���Ł{�}�łւ̏����j
				if( lstSearchAry_eda.size() > 0 ){

					//�}�Ԑ����[�v
					for(int j = 0; j < lstSearchAry_eda.size(); j++){

						//�}�Ԏ擾
						Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
						int roop_no_eda = Integer.parseInt(toString(items_eda[3]));

						//�c�ƃX�e�[�^�X�擾
						int st_eigyo = Integer.parseInt(toString(items_eda[6]));

						//�c�ƃX�e�[�^�X��4�����̎}�ł�􂢑ւ�
						if(st_eigyo < 4 ){

							//�X�e�[�^�X�̍X�V
							strSQL = null;
							strSQL = makeUpdateSQL_T441(cd_shain, nen, no_oi, roop_no_eda);
							//DB�X�V
							this.execDB.execSQL(strSQL.toString());

							//�X�e�[�^�X�����̓o�^
							strSQL = null;
							strSQL = makeExecSQL_T442(cd_shain, nen, no_oi,roop_no_eda);
							//DB�X�V
							this.execDB.execSQL(strSQL.toString());

						}
					}
				}
				//�}�ł��Ȃ��ꍇ�i���ł����̏����j
				else{
					//�X�e�[�^�X�̓o�^
					strSQL = null;
					strSQL = makeInsertSQL_T441(cd_shain, nen, no_oi, 0);
					//DB�X�V
					this.execDB.execSQL(strSQL.toString());

					//�X�e�[�^�X�����̓o�^
					strSQL = null;
					strSQL = makeExecSQL_T442(cd_shain, nen, no_oi, 0);
					//DB�X�V
					this.execDB.execSQL(strSQL.toString());
				}
			}

			//DB�R�~�b�g
			this.searchDB.Commit();

		}catch(Exception e){
			//DB���[���o�b�N
			this.searchDB.Rollback();

			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{
			//DB�R�l�N�V�����J��
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}

			//���[�J���ϐ��̊J��
			removeList(lstSearchAry);
			strSQL = null;

		}

		//�A�������Z�ް��̉�ЍH��Ō����̐􂢑ւ����s���B

		try{

			//�􂢑ւ����sFg���Z�b�g����Ă���ꍇ�i�V�K�Ɏ��Z�˗������f�[�^������ꍇ�j
			if( blnAraigae ){

				//�yQP@00342�z
				//�}�Ԃ�����ꍇ�i���Ł{�}�łւ̏����j
				if( lstSearchAry_eda.size() > 0 ){

					//�}�Ԑ����[�v
					for(int j = 0; j < lstSearchAry_eda.size(); j++){

						//�}�Ԏ擾
						Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
						int roop_no_eda = Integer.parseInt(toString(items_eda[3]));

						//�c�ƃX�e�[�^�X�擾
						int st_eigyo = Integer.parseInt(toString(items_eda[6]));

						//�c�ƃX�e�[�^�X��4�����̎}�ł�􂢑ւ�
						if(st_eigyo < 4 ){

							//��ЁA�����擾
							int cd_kaisha =  Integer.parseInt(toString(items_eda[4]));
							int cd_busho =  Integer.parseInt(toString(items_eda[5]));

							//�}�ԍ����ɐ􂢑ւ�
							CGEN0010_Logic clsCGEN0010_Logic = new CGEN0010_Logic();
							clsCGEN0010_Logic.ExecLogic(cd_shain, nen, no_oi, cd_kaisha, cd_busho, roop_no_eda , userInfo);

						}
					}
				}
				//�}�ł��Ȃ��ꍇ�i���ł����̏����j
				else{
					//�}�ԍ�0�Ő􂢑ւ�
					CGEN0010_Logic clsCGEN0010_Logic = new CGEN0010_Logic();
					clsCGEN0010_Logic.ExecLogic(cd_shain, nen, no_oi, cls_cd_kaisha, cls_cd_kojo, 0 , userInfo);
				}
			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�f�[�^�̌����A���/�H��􂢑ւ������s���܂����B");

		}finally{

		}

	}

	/**�yQP@00342�z
	 * ���Z�@����ið��ٍX�V�@�i�˗��񐔁j
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param lstRecset�F��������
	 * @return strSql�F�X�VSQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer makeExecSQL_sisanShisakuhin(
			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,String no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {
			//SQL���̍쐬
			strSql.append(" UPDATE tr_shisan_shisakuhin ");
			strSql.append(" SET  ");
			strSql.append("        su_irai = ( ");
			strSql.append(" 				select  ");
			strSql.append(" 					max(su_irai)+1 AS su_irai ");
			strSql.append(" 				from ");
			strSql.append(" 					tr_shisan_shisakuhin ");
			strSql.append(" 				where ");
			strSql.append(" 					cd_shain = " + cd_shain );
			strSql.append(" 					AND nen =" + nen );
			strSql.append(" 					AND no_oi =" + no_oi );
			strSql.append(" 					AND no_eda =" + no_eda );
			strSql.append(" 			) ");
			strSql.append("       ,id_koshin = 9999999999 ");
			strSql.append("       ,dt_koshin = GETDATE() ");
			strSql.append(" WHERE  ");
			strSql.append("       cd_shain =  " + cd_shain );
			strSql.append("       AND nen = " + nen );
			strSql.append("       AND no_oi = " + no_oi );
			strSql.append("       AND no_eda = " + no_eda );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * ���Z�@�z���e�[�u���iT320�j�o�^�����A�����pSQL�𐶐�����
	 * @param cd_shain		�F����CD�@�Ј�ID
	 * @param nen			�F����CD�@�N
	 * @param no_oi			�F����CD�@�ǔ�
	 * @return StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T320(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT ");
			ret.append("  T120.cd_shain  AS cd_shain ");
			ret.append(" ,T120.nen       AS nen ");
			ret.append(" ,T120.no_oi     AS no_oi ");
			ret.append(" ,T120.cd_kotei  AS cd_kotei ");
			ret.append(" ,T120.seq_kotei AS seq_kotei ");
			ret.append(" ,T320.cd_shain  AS cd_shain_SHISAN ");
			ret.append(" ,T320.nen       AS nen_SHISAN ");
			ret.append(" ,T320.no_oi     AS no_oi_SHISAN ");
			ret.append(" ,T320.cd_kotei  AS cd_kotei_SHISAN ");
			ret.append(" ,T320.seq_kotei AS seq_kotei_SHISAN ");
			ret.append(" ,T120.nm_genryo AS nm_genryo ");
			ret.append(" ,T120.tanka     AS tanka ");
			ret.append(" ,T120.budomari  AS budomari ");
			ret.append(" FROM tr_haigo AS T120 ");
			ret.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			ret.append("       ON  T120.cd_shain    = T320.cd_shain ");
			ret.append("       AND T120.nen         = T320.nen ");
			ret.append("       AND T120.no_oi       = T320.no_oi ");
			ret.append("       AND T120.cd_kotei    = T320.cd_kotei ");
			ret.append("       AND T120.seq_kotei   = T320.seq_kotei ");
			ret.append(" WHERE ");
			ret.append("           T120.cd_shain    = " + cd_shain + " ");
			ret.append("       AND T120.nen         = " + nen + " ");
			ret.append("       AND T120.no_oi       = " + no_oi + " ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}


	/**�yQP@00342�z
	 * �}�ԃf�[�^����SQL����
	 * @param cd_shain	�F����CD�@�Ј�ID
	 * @param nen		�F����CD�@�N
	 * @param no_oi		�F����CD�@�ǔ�
	 * @return	StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_Eda(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT  ");
			ret.append("  T310.cd_shain  ");
			ret.append(" ,T310.nen  ");
			ret.append(" ,T310.no_oi ");
			ret.append(" ,T310.no_eda ");
			ret.append(" ,T310.cd_kaisha ");
			ret.append(" ,T310.cd_kojo ");
			ret.append(" ,T441.st_eigyo ");
			ret.append(" ,'false' AS arai "); //�􂢑ւ��t���O�i�����l�Ffalse�j
			ret.append(" FROM tr_shisan_shisakuhin AS T310 ");

			ret.append(" LEFT JOIN tr_shisan_status AS T441 ");
			ret.append(" ON T310.cd_shain = T441.cd_shain ");
			ret.append(" AND T310.nen = T441.nen ");
			ret.append(" AND T310.no_oi = T441.no_oi ");
			ret.append(" AND T310.no_eda = T441.no_eda ");

			ret.append(" WHERE  ");
			ret.append("      T310.cd_shain = " + cd_shain + " ");
			ret.append("  AND T310.nen      = " + nen + " ");
			ret.append("  AND T310.no_oi    = " + no_oi + " ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**�yQP@00342�z
	 * ���Z�@����i�e�[�u���iT310�j�̌���SQL����
	 * @param cd_shain	�F����CD�@�Ј�ID
	 * @param nen		�F����CD�@�N
	 * @param no_oi		�F����CD�@�ǔ�
	 * @return	StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T310(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT  ");
			ret.append("  T310.cd_shain  ");
			ret.append(" ,T310.nen  ");
			ret.append(" ,T310.no_oi ");
			ret.append(" ,T110.cd_kaisha AS T110_kaisha");
			ret.append(" ,T110.cd_kojo AS T110_kojo");
			ret.append(" ,T310.cd_kaisha AS T310_kaisha ");
			ret.append(" ,T310.cd_kojo AS T310_kojo ");
			ret.append(" FROM tr_shisakuhin AS T110 ");
			ret.append(" LEFT JOIN tr_shisan_shisakuhin AS T310 ");
			ret.append(" ON   T110.cd_shain = T310.cd_shain ");
			ret.append("  AND T110.nen      = T310.nen      ");
			ret.append("  AND T110.no_oi    = T310.no_oi       ");
			ret.append(" WHERE  ");
			ret.append("      T110.cd_shain = " + cd_shain + " ");
			ret.append("  AND T110.nen      = " + nen + " ");
			ret.append("  AND T110.no_oi    = " + no_oi + " ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**�yQP@00342�z
	 * ���Z�@�������Z�����iT311�j�̓o�^SQL����
	 * @param cd_shain	�F����CD�@�Ј�ID
	 * @param nen		�F����CD�@�N
	 * @param no_oi		�F����CD�@�ǔ�
	 * @return	StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T311(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_memo  ");
			ret.append("            (cd_shain  ");
			ret.append("            ,nen  ");
			ret.append("            ,no_oi  ");
			ret.append("            ,memo  ");
			ret.append("            ,id_toroku  ");
			ret.append("            ,dt_toroku  ");
			ret.append("            ,id_koshin  ");
			ret.append("            ,dt_koshin)  ");
			ret.append("      VALUES  ");
			ret.append("            (" + cd_shain);
			ret.append("            ," + nen);
			ret.append("            ," + no_oi);
			ret.append("            ,NULL");
			ret.append("            ," + userInfoData.getId_user());
			ret.append("            ,GETDATE()");
			ret.append("            ,NULL");
			ret.append("            ,NULL )");


		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**�yQP@00342�z
	 * ���Z�@�������Z�����iT312�j�̓o�^SQL����
	 * @param cd_shain	�F����CD�@�Ј�ID
	 * @param nen		�F����CD�@�N
	 * @param no_oi		�F����CD�@�ǔ�
	 * @return	StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T312(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_memo_eigyo  ");
			ret.append("            (cd_shain  ");
			ret.append("            ,nen  ");
			ret.append("            ,no_oi  ");
			ret.append("            ,memo_eigyo  ");
			ret.append("            ,id_toroku  ");
			ret.append("            ,dt_toroku  ");
			ret.append("            ,id_koshin  ");
			ret.append("            ,dt_koshin)  ");
			ret.append("      VALUES  ");
			ret.append("            (" + cd_shain);
			ret.append("            ," + nen);
			ret.append("            ," + no_oi);
			ret.append("            ,NULL");
			ret.append("            ," + userInfoData.getId_user());
			ret.append("            ,GETDATE()");
			ret.append("            ,NULL");
			ret.append("            ,NULL )");


		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**�yQP@00342�z
	 * ���Z�@�X�e�[�^�X�iT441�j�̓o�^SQL����
	 * @param cd_shain	�F����CD�@�Ј�ID
	 * @param nen		�F����CD�@�N
	 * @param no_oi		�F����CD�@�ǔ�
	 * @return	StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeInsertSQL_T441(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			//�X�e�[�^�X����ǉ�
			ret.append(" INSERT INTO tr_shisan_status  ");
			ret.append("            (cd_shain  ");
			ret.append("            ,nen  ");
			ret.append("            ,no_oi  ");
			ret.append("            ,no_eda  ");
			ret.append("            ,st_kenkyu  ");
			ret.append("            ,st_seisan  ");
			ret.append("            ,st_gensizai  ");
			ret.append("            ,st_kojo  ");
			ret.append("            ,st_eigyo  ");
			ret.append("            ,id_toroku  ");
			ret.append("            ,dt_toroku  ");
			ret.append("            ,id_koshin  ");
			ret.append("            ,dt_koshin)  ");
			ret.append("      VALUES  ");
			ret.append("            (" + cd_shain);
			ret.append("            ," + nen);
			ret.append("            ," + no_oi);
			ret.append("            ," + no_eda);
			ret.append("            ,2");
			ret.append("            ,1");
			ret.append("            ,1");
			ret.append("            ,1");
			ret.append("            ,1");
			ret.append("            ," + userInfoData.getId_user());
			ret.append("            ,GETDATE()");
			ret.append("            ,NULL");
			ret.append("            ,NULL )");


		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**�yQP@00342�z
	 * ���Z�@�X�e�[�^�X�iT441�j�̓o�^SQL����
	 * @param cd_shain	�F����CD�@�Ј�ID
	 * @param nen		�F����CD�@�N
	 * @param no_oi		�F����CD�@�ǔ�
	 * @return	StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeUpdateSQL_T441(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			//�X�e�[�^�X�e�[�u���X�V
			ret.append(" UPDATE tr_shisan_status ");
			ret.append("    SET  ");
			ret.append("        st_kenkyu = 2");
			ret.append("       ,st_seisan = 1");
			ret.append("       ,st_gensizai = 1");
			ret.append("       ,st_kojo = 1");
			ret.append("       ,st_eigyo = 1");
			ret.append("       ,id_koshin = " + userInfoData.getId_user());
			ret.append("       ,dt_koshin = GETDATE()");
			ret.append("  WHERE ");
			ret.append(" 	  cd_shain =  " + cd_shain);
			ret.append("       AND nen =  " + nen);
			ret.append("       AND no_oi =  " + no_oi);
			ret.append("       AND no_eda =  " + no_eda);

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}

	/**�yQP@00342�z
	 * ���Z�@�X�e�[�^�X�����iT442�j�̓o�^SQL����
	 * @param cd_shain	�F����CD�@�Ј�ID
	 * @param nen		�F����CD�@�N
	 * @param no_oi		�F����CD�@�ǔ�
	 * @return	StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T442(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			//�X�e�[�^�X����ǉ�
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
			ret.append("            (" + cd_shain + " ");
			ret.append("            ," + nen + " ");
			ret.append("            ," + no_oi + " ");
			ret.append("            ," + no_eda +" ");
			ret.append("            ,GETDATE() ");
			ret.append("            ," + userInfoData.getCd_kaisha() + " ");
			ret.append("            ," + userInfoData.getCd_busho() + " ");
			ret.append("            ," + userInfoData.getId_user() + " ");
			ret.append("            ,'wk_kenkyu'");
			ret.append("            ,'1' ");
			ret.append("            ,2 ");
			ret.append("            ,1 ");
			ret.append("            ,1 ");
			ret.append("            ,1 ");
			ret.append("            ,1 ");
			ret.append("            ," + userInfoData.getId_user() + " ");
			ret.append("            ,GETDATE() )");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * ���Z�@����e�[�u���iT331�j�̌����pSQL�𐶐�����
	 * @param cd_shain		�F����CD�@�Ј�ID
	 * @param nen			�F����CD�@�N
	 * @param no_oi			�F����CD�@�ǔ�
	 * @param seq_shisaku	�F����SEQ
	 * @return StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T331(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int    seq_shisaku
			,String    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

//			ret.append(" SELECT ");
//			ret.append("  cd_shain ");
//			ret.append(" ,nen ");
//			ret.append(" ,no_oi ");
//			ret.append(" ,seq_shisaku ");
//			ret.append(" ,no_eda ");
//			ret.append(" FROM tr_shisan_shisaku ");
//			ret.append(" WHERE ");
//			ret.append("      cd_shain    = " + cd_shain + " ");
//			ret.append("  AND nen         = " + nen + " ");
//			ret.append("  AND no_oi       = " + no_oi + " ");
//			ret.append("  AND seq_shisaku = " + seq_shisaku + " ");

			//�yQP@00342�z
			ret.append(" SELECT ");
			ret.append("  T331.cd_shain ");
			ret.append(" ,T331.nen ");
			ret.append(" ,T331.no_oi ");
			ret.append(" ,T331.seq_shisaku ");
			ret.append(" ,T331.no_eda ");
			ret.append(" FROM ");
			ret.append(" tr_shisan_shisaku AS T331 ");
			ret.append(" LEFT JOIN tr_shisan_status AS T441 ");
			ret.append(" ON T441.cd_shain = T331.cd_shain  ");
			ret.append(" AND T441.nen = T331.nen ");
			ret.append(" AND T441.no_oi = T331.no_oi ");
			ret.append(" AND T441.no_eda = T331.no_eda ");
			ret.append("      cd_shain    = " + cd_shain + " ");
			ret.append("  AND nen         = " + nen + " ");
			ret.append("  AND no_oi       = " + no_oi + " ");
			ret.append("  AND seq_shisaku = " + seq_shisaku + " ");
			ret.append("  AND no_eda       = " + no_eda + " ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * ���Z�@�z���e�[�u���iT320�j�o�^�����A�����pSQL�𐶐�����i���Łj
	 * @param cd_shain		�F����CD�@�Ј�ID
	 * @param nen			�F����CD�@�N
	 * @param no_oi			�F����CD�@�ǔ�
	 * @return StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T320_motohan(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT ");
			ret.append("  T120.cd_shain  AS cd_shain ");
			ret.append(" ,T120.nen       AS nen ");
			ret.append(" ,T120.no_oi     AS no_oi ");
			ret.append(" ,T120.cd_kotei  AS cd_kotei ");
			ret.append(" ,T120.seq_kotei AS seq_kotei ");
			ret.append(" ,T320.cd_shain  AS cd_shain_SHISAN ");
			ret.append(" ,T320.nen       AS nen_SHISAN ");
			ret.append(" ,T320.no_oi     AS no_oi_SHISAN ");
			ret.append(" ,T320.cd_kotei  AS cd_kotei_SHISAN ");
			ret.append(" ,T320.seq_kotei AS seq_kotei_SHISAN ");
			ret.append(" ,T120.nm_genryo AS nm_genryo ");
			ret.append(" ,T120.tanka     AS tanka ");
			ret.append(" ,T120.budomari  AS budomari ");
			ret.append(" ,0  AS no_eda ");
			ret.append(" FROM tr_haigo AS T120 ");
			ret.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			ret.append("       ON  T120.cd_shain    = T320.cd_shain ");
			ret.append("       AND T120.nen         = T320.nen ");
			ret.append("       AND T120.no_oi       = T320.no_oi ");
			ret.append("       AND T120.cd_kotei    = T320.cd_kotei ");
			ret.append("       AND T120.seq_kotei   = T320.seq_kotei ");
			ret.append(" WHERE ");
			ret.append("           T120.cd_shain    = " + cd_shain + " ");
			ret.append("       AND T120.nen         = " + nen + " ");
			ret.append("       AND T120.no_oi       = " + no_oi + " ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * ���Z�@�z���e�[�u���iT320�j�o�^�����A�����pSQL�𐶐�����i���Łj
	 * @param cd_shain		�F����CD�@�Ј�ID
	 * @param nen			�F����CD�@�N
	 * @param no_oi			�F����CD�@�ǔ�
	 * @return StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T320_edahan(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,String    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT ");
			ret.append("  T120.cd_shain  AS cd_shain ");
			ret.append(" ,T120.nen       AS nen ");
			ret.append(" ,T120.no_oi     AS no_oi ");
			ret.append(" ,T120.cd_kotei  AS cd_kotei ");
			ret.append(" ,T120.seq_kotei AS seq_kotei ");
			ret.append(" ,T320.cd_shain  AS cd_shain_SHISAN ");
			ret.append(" ,T320.nen       AS nen_SHISAN ");
			ret.append(" ,T320.no_oi     AS no_oi_SHISAN ");
			ret.append(" ,T320.cd_kotei  AS cd_kotei_SHISAN ");
			ret.append(" ,T320.seq_kotei AS seq_kotei_SHISAN ");
			ret.append(" ,T120.nm_genryo AS nm_genryo ");
			ret.append(" ,T120.tanka     AS tanka ");
			ret.append(" ,T120.budomari  AS budomari ");
			ret.append(" ,T320.no_eda  AS no_eda ");
			ret.append(" FROM tr_haigo AS T120 ");
			ret.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			ret.append("       ON  T120.cd_shain    = T320.cd_shain ");
			ret.append("       AND T120.nen         = T320.nen ");
			ret.append("       AND T120.no_oi       = T320.no_oi ");
			ret.append("       AND T120.cd_kotei    = T320.cd_kotei ");
			ret.append("       AND T120.seq_kotei   = T320.seq_kotei ");
			ret.append(" WHERE ");
			ret.append("           T320.cd_shain    = " + cd_shain + " ");
			ret.append("       AND T320.nen         = " + nen + " ");
			ret.append("       AND T320.no_oi       = " + no_oi + " ");

			//�yQP@00342�z
			ret.append("       AND T320.no_eda       = " + no_eda + " ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * ���Z�@����i�e�[�u���iT310�j�ǉ��pSQL�𐶐�����
	 * @param cd_shain		�F����CD�@�Ј�ID
	 * @param nen			�F����CD�@�N
	 * @param no_oi			�F����CD�@�ǔ�
	 * @return StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T310(

			BigDecimal             	cd_shain
			,int                nen
			,int                no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_shisakuhin ");
			ret.append(" ( ");
			ret.append("  cd_shain ");
			ret.append(" ,nen ");
			ret.append(" ,no_oi ");

			//�yQP@00342�z
			ret.append(" ,no_eda ");

			ret.append(" ,cd_kaisha ");
			ret.append(" ,cd_kojo ");
			ret.append(" ,su_iri ");
			ret.append(" ,genka ");
			ret.append(" ,baika ");
			ret.append(" ,cd_genka_tani ");
			// DEL 2013/9/6 okano�yQP@30151�zNo.30 start
			//ret.append(" ,buturyo ");
			// DEL 2013/9/6 okano�yQP@30151�zNo.30 end
			ret.append(" ,dt_hatubai ");
			ret.append(" ,uriage_k ");
			ret.append(" ,rieki_k ");
			ret.append(" ,uriage_h ");
			ret.append(" ,rieki_h ");
			ret.append(" ,cd_nisugata ");
			ret.append(" ,lot ");
			ret.append(" ,saiyo_sample ");
			ret.append(" ,ken_id_koshin ");
			ret.append(" ,ken_dt_koshin ");
			ret.append(" ,sei_id_koshin ");
			ret.append(" ,sei_dt_koshin ");
			ret.append(" ,gen_id_koshin ");
			ret.append(" ,gen_dt_koshin ");
			ret.append(" ,kojo_id_koshin ");
			ret.append(" ,kojo_dt_koshin ");
			ret.append(" ,sam_dt_koshin ");
			ret.append(" ,fg_keisan ");
			ret.append(" ,id_toroku ");
			ret.append(" ,dt_toroku ");
			ret.append(" ,id_koshin ");
			ret.append(" ,dt_koshin ");

			//�yQP@00342�z
			ret.append(" ,yoryo ");
			ret.append(" ,su_irai ");
			//ADD 2013/10/22 QP@30154 okano start
			ret.append(" ,cd_hanseki ");
			//ADD 2013/10/22 QP@30154 okano end

			ret.append(" ) ");
			ret.append(" SELECT ");
			ret.append("  cd_shain ");
			ret.append(" ,nen ");
			ret.append(" ,no_oi ");

			//�yQP@00342�z
			ret.append(" ,0 ");

			ret.append(" ,cd_kaisha ");
			ret.append(" ,cd_kojo ");
			ret.append(" ,su_iri ");
			ret.append(" ,genka ");
			ret.append(" ,baika ");
			ret.append(" ,NULL ");
			// DEL 2013/9/6 okano�yQP@30151�zNo.30 start
			//ret.append(" ,buturyo ");
			// DEL 2013/9/6 okano�yQP@30151�zNo.30 end
			ret.append(" ,dt_hatubai ");
			ret.append(" ,uriage_k ");
			ret.append(" ,rieki_k ");
			ret.append(" ,uriage_h ");
			ret.append(" ,rieki_h ");
//2011/11/30 �yQP@10713�z�׎p�����ҏW�����ݒ�ɕύX�@TT H.SHIMA Start
//			ret.append(" ,cd_nisugata ");
			ret.append(" ,replace(convert(varchar, convert(MONEY, yoryo), 1), '.00', '')+nm_literal+'/'+replace(convert(varchar, convert(MONEY,su_iri),1),'.00','') AS cd_nisugata ");
//2011/11/30 �yQP@10713�z�׎p�����ҏW�����ݒ�ɕύX�@TT H.SHIMA End
			ret.append(" ,NULL ");

// 2010/05/19�@�V�T�N�C�b�N�i�����j�@���Z�����̏����l��NULL�ɕύX�@TT.NISHIGAWA�@START
			//ret.append(" ,memo_shisaku ");
//			ret.append(" ,NULL ");
// 2010/05/19�@�V�T�N�C�b�N�i�����j�@���Z�����̏����l��NULL�ɕύX�@TT.NISHIGAWA�@END

			ret.append(" ,NULL ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,1 ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");

			//�yQP@00342�z
			ret.append(" ,yoryo");
			ret.append(" ,1");
			//ADD 2013/10/22 QP@30154 okano start
			ret.append(" ,cd_hanseki ");
			//ADD 2013/10/22 QP@30154 okano end

			ret.append(" FROM ");
			ret.append(" tr_shisakuhin ");
//2011/11/30 �yQP@10713�z�׎p�����ҏW�����ݒ�ɕύX�@TT H.SHIMA Start
			ret.append(" ,ma_literal");
//2011/11/30 �yQP@10713�z�׎p�����ҏW�����ݒ�ɕύX�@TT H.SHIMA End
			ret.append(" WHERE ");
			ret.append("     cd_shain = " + cd_shain + " ");
			ret.append(" AND nen      = " + nen + " ");
			ret.append(" AND no_oi    = " + no_oi + " ");
//2011/11/30 �yQP@10713�z�׎p�����ҏW�����ݒ�ɕύX�@TT H.SHIMA Start
			ret.append(" AND cd_literal = cd_tani ");
			ret.append(" AND cd_category = 'K_tani' ");
//2011/11/30 �yQP@10713�z�׎p�����ҏW�����ݒ�ɕύX�@TT H.SHIMA End

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * ���Z�@����e�[�u���iT331�j�ǉ��pSQL�̐���
	 * @param cd_shain		�F����CD�@�Ј�ID
	 * @param nen			�F����CD�@�N
	 * @param no_oi			�F����CD�@�ǔ�
	 * @param seq_shisaku	�F����SEQ
	 * @return StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T331(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int    seq_shisaku
			,String    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_shisaku ");
			ret.append(" ( ");
			ret.append("  cd_shain ");				//0
			ret.append(" ,nen ");					//1
			ret.append(" ,no_oi ");					//2
			ret.append(" ,seq_shisaku ");			//3

			//�yQP@00342�z
			ret.append(" ,no_eda ");

			ret.append(" ,juryo_shiagari_g ");		//4
			ret.append(" ,dt_shisan ");				//5
			ret.append(" ,budomari ");				//6
			ret.append(" ,heikinjuten ");			//7
			ret.append(" ,cs_kotei ");				//8
			ret.append(" ,kg_kotei ");				//9
			ret.append(" ,id_toroku ");				//10
			ret.append(" ,dt_toroku ");				//11
			ret.append(" ,id_koshin ");				//12
			ret.append(" ,dt_koshin ");				//13
//ADD 2013/07/9 ogawa �yQP@30151�zNo.13 start
			//���ڌŒ�`�F�b�N ON(1)���Z�b�g
			ret.append(" ,fg_koumokuchk ");
//ADD 2013/07/09 ogawa �yQP@30151�zNo.13 end
			//ADD 2013/10/22 QP@30154 okano start
			ret.append(" ,cs_rieki ");
			ret.append(" ,kg_rieki ");
			//ADD 2013/10/22 QP@30154 okano end
			ret.append(" ) ");
			ret.append(" SELECT  ");
			ret.append("  T131.cd_shain ");			//0
			ret.append(" ,T131.nen ");				//1
			ret.append(" ,T131.no_oi ");			//2
			ret.append(" ,T131.seq_shisaku ");		//3

			//�yQP@00342�z
			ret.append(" ," + no_eda);			//2

			ret.append(" ,T131.juryo_shiagari_g ");	//4
			ret.append(" ,NULL ");					//5
			ret.append(" ,NULL ");                    //6
			ret.append(" ,NULL ");					//7

//			ret.append(" ,CASE IsNull(T141.yukobudomari,'') WHEN '' ");
//			ret.append(" THEN NULL ");
//			ret.append(" ELSE T141.yukobudomari ");
//			ret.append(" END AS budomari ");		//6
//
//			ret.append(" ,CASE IsNull(T141.heikinzyu,'') WHEN '' ");
//			ret.append(" THEN NULL ");
//			ret.append(" ELSE T141.yukobudomari ");
//			ret.append(" END AS heikinzyu ");		//7

			ret.append(" ,NULL ");					//8
			ret.append(" ,NULL ");					//9
			ret.append(" ,9999 ");					//10
			ret.append(" ,GETDATE() ");				//11
			ret.append(" ,9999 ");					//12
			ret.append(" ,GETDATE() ");				//13

//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 ���f�t�H���g�̓`�F�b�N�I�tstart
//ADD 2013/07/9 ogawa �yQP@30151�zNo.13 start
			//���ڌŒ�`�F�b�N ON(1)���Z�b�g
			//ret.append(" ,1 ");
			ret.append(" ,0 ");
//ADD 2013/07/09 ogawa �yQP@30151�zNo.13 end
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

			//ADD 2013/10/22 QP@30154 okano start
			ret.append(" ,NULL ");
			ret.append(" ,NULL ");
			//ADD 2013/10/22 QP@30154 okano end
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_genryo AS T141 ");
			ret.append(" ON  T131.cd_shain    = T141.cd_shain ");
			ret.append(" AND T131.nen         = T141.nen ");
			ret.append(" AND T131.no_oi       = T141.no_oi ");
			ret.append(" AND T131.seq_shisaku = T141.seq_shisaku ");
			ret.append(" WHERE ");
			ret.append("     T131.cd_shain = " + cd_shain + " ");
			ret.append(" AND T131.nen      = " + nen + " ");
			ret.append(" AND T131.no_oi    = " + no_oi + " ");
			ret.append(" AND T131.seq_shisaku  = " + seq_shisaku + " ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * ���Z�@�z���e�[�u���iT320�j�̒ǉ��pSQL�𐶐�����
	 * @param items	�F������������
	 * @return StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T320(

			Object[] items
			,String no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_haigo ");
			ret.append(" ( ");
			ret.append("  cd_shain     ");
			ret.append(" ,nen          ");
			ret.append(" ,no_oi        ");

			//�yQP@00342�z
			ret.append(" ,no_eda        ");


			ret.append(" ,cd_kotei     ");
			ret.append(" ,seq_kotei    ");
			ret.append(" ,nm_genryo    ");
			ret.append(" ,tanka_ins ");
			ret.append(" ,budomari_ins ");
			ret.append(" ,tanka_ma ");
			ret.append(" ,budomar_ma ");
			ret.append(" ,id_toroku ");
			ret.append(" ,dt_toroku ");
			ret.append(" ,id_koshin ");
			ret.append(" ,dt_koshin ");
			ret.append(" ) ");
			ret.append(" VALUES ");
			ret.append(" ( ");
			ret.append("  " + toString(items[0]) + " ");
			ret.append(" ," + toString(items[1]) + " ");
			ret.append(" ," + toString(items[2]) + " ");

			//�yQP@00342�z
			ret.append(" ," + no_eda + " ");

			ret.append(" ," + toString(items[3]) + " ");
			ret.append(" ," + toString(items[4]) + " ");
			ret.append(" ,'" + toString(items[10]) + "' ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ," + items[11] + " ");
			ret.append(" ," + items[12] + " ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,getdate() ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,getdate() ");
			ret.append(" ) ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}


	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	/**
	 * ���Z�@��{���e�[�u���iT313�j�̒ǉ��pSQL�𐶐�����
	 * @param cd_shain		�F����CD�@�Ј�ID
	 * @param nen			�F����CD�@�N
	 * @param no_oi			�F����CD�@�ǔ�
	 * @param seq_shisaku	�F����SEQ
	 * @param no_eda		�F�}��
	 * @return StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T313(

			BigDecimal cd_shain
			,int       nen
			,int       no_oi
			,int       seq_shisaku
			,String    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_kihonjoho ");
			ret.append(" ( ");
			ret.append("  cd_shain ");
			ret.append(" ,nen ");
			ret.append(" ,no_oi ");
			ret.append(" ,seq_shisaku ");
			ret.append(" ,no_eda ");
			ret.append(" ,genka ");
			ret.append(" ,baika ");
			ret.append(" ,cd_genka_tani ");
			// DEL 2013/9/6 okano�yQP@30151�zNo.30 start
			//ret.append(" ,buturyo ");
			// DEL 2013/9/6 okano�yQP@30151�zNo.30 end
			ret.append(" ,dt_hatubai ");
			ret.append(" ,uriage_k ");
			ret.append(" ,rieki_k ");
			ret.append(" ,uriage_h ");
			ret.append(" ,rieki_h ");
			ret.append(" ,id_toroku ");
			ret.append(" ,dt_toroku ");
			ret.append(" ,id_koshin ");
			ret.append(" ,dt_koshin ");
			ret.append(" ) ");
			ret.append(" SELECT ");
			ret.append("  T310.cd_shain ");			//�Ј�
			ret.append(" ,T310.nen ");				//�N
			ret.append(" ,T310.no_oi ");			//�ǔ�
			ret.append(" ," + seq_shisaku + " ");	//����SEQ
			ret.append(" ," + no_eda + " ");		//�}��
			ret.append(" ,T310.genka ");			//��]����
			ret.append(" ,T310.baika ");			//��]����
			ret.append(" ,NULL ");					//�����P�ʃR�[�h
			// DEL 2013/9/6 okano�yQP@30151�zNo.30 start
			//ret.append(" ,T310.buturyo ");			//�z�蕨��
			// DEL 2013/9/6 okano�yQP@30151�zNo.30 end
			ret.append(" ,T310.dt_hatubai ");		//����[�i����
			ret.append(" ,T310.uriage_k ");			//�v�攄��
			ret.append(" ,T310.rieki_k ");			//�v�旘�v
			ret.append(" ,T310.uriage_h ");			//�̔��㔄��
			ret.append(" ,T310.rieki_h ");			//�̔��㗘�v
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");
			ret.append(" FROM ");
			ret.append(" tr_shisan_shisakuhin T310 ");
			ret.append(" INNER JOIN tr_shisakuhin T110 ");
			ret.append(" ON  T310.cd_shain = T110.cd_shain ");
			ret.append(" AND T310.nen = T110.nen ");
			ret.append(" AND T310.no_oi = T110.no_oi ");
			ret.append(" ,ma_literal M_Lit ");
			ret.append(" WHERE ");
			ret.append("     T310.cd_shain = " + cd_shain + " ");
			ret.append(" AND T310.nen      = " + nen + " ");
			ret.append(" AND T310.no_oi    = " + no_oi + " ");

			//ADD 2014/01/21 nishigawa �yQP@30151�zNo.37 start
			ret.append(" AND T310.no_eda   = 0 ");
			//ADD 2014/01/21 nishigawa �yQP@30151�zNo.37 end

			ret.append(" AND M_Lit.cd_literal = cd_tani ");
			ret.append(" AND M_Lit.cd_category = 'K_tani' ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end


	//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
	/**
	 * ���Z�������N���A
	 * @param cd_shain		�F����CD�@�Ј�ID
	 * @param nen			�F����CD�@�N
	 * @param no_oi			�F����CD�@�ǔ�
	 * @return StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_DtShisanClear(
			BigDecimal cd_shain
			,int       nen
			,int       no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer ret = new StringBuffer();
		try{
			ret.append(" update tr_shisan_shisaku ");
			ret.append(" set dt_shisan=null ");
			ret.append(" where cd_shain=" + cd_shain);
			ret.append(" and nen=" + nen);
			ret.append(" and no_oi=" + no_oi);
			//ADD 2015/09/15 kitazawa �yQP@30154�z�ǉ��ۑ�No.11 �s��C�� start
			ret.append(" and fg_koumokuchk <> 1");
			//ADD 2015/09/15 kitazawa �yQP@30154�z�ǉ��ۑ�No.11 �s��C�� end
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");
		}finally{

		}
		return ret;
	}
}
