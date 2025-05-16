package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �ύX�m�F
 * ���͓��e��DB���r���ύX�̗L�����m�F����
 * @author isono
 * @create 2009/11/03
 */
public class FGEN0020_Logic extends LogicBase {

	//�Ώۃf�[�^������
	//����No-�Ј�ID
	BigDecimal cd_shisaku_syainID = new BigDecimal(-1);
	//����No-�N
	int cd_shisaku_nen = -1;
	//����No-�ǔ�
	int cd_shisaku_oi = -1;
	
	//�yQP@00342�z����No-�}��
	int cd_shisaku_eda = -1;
	
	//������
	List<?> listShisaku = null;	
	//����ُ��
	List<?> listSanpuru = null;	
	//�������
	List<?> listGenryo = null;	
	//���ޏ��
	List<?> listShizai = null;
	
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	//��{���T�u
	List<?> listKihonSub = null;
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

	/**
	 * �R���X�g���N�^
	 */
	public FGEN0020_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean ret = null;
		
		try {

			//����NO�ޔ�	
			cd_shisaku_syainID = toDecimal(reqData.getFieldVale("kihon", "rec", "cd_shain"),"-1");
			cd_shisaku_nen = toInteger(reqData.getFieldVale("kihon", "rec", "nen"),-1);
			cd_shisaku_oi = toInteger(reqData.getFieldVale("kihon", "rec", "no_oi"),-1);

			//�yQP@00342�z
			cd_shisaku_eda = toInteger(reqData.getFieldVale("kihon", "rec", "no_eda"),-1);
			
			//DB�擾���ڂ̎擾
			getDBKomoku();
			
			//���X�|���X����
			ret = new RequestResponsKindBean();
			ret.addFieldVale("table", "rec", "flg_return", "true");
			ret.addFieldVale("table", "rec", "msg_error", "");
			ret.addFieldVale("table", "rec", "nm_class", "");
			ret.addFieldVale("table", "rec", "no_errmsg", "");
			ret.addFieldVale("table", "rec", "cd_error", "");
			ret.addFieldVale("table", "rec", "msg_system", "");
			
			ret.setID("FGEN0020");
			
			//�ύX�m�F
			if (Comparison(reqData)){
				//�ύX�Ȃ�
				ret.addFieldVale("table", "rec", "hantei", "true");
				
			}else{
				//�ύX����
				ret.addFieldVale("table", "rec", "hantei", "false");
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�@�ύX�m�F�����s���܂����B");
			
		} finally {
			if (execDB != null) {
				execDB.Close();				//�Z�b�V�����̃N���[�Y
				execDB = null;
			}
		}
		return ret;
		
	}
	private boolean Comparison(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		boolean ret = true;
		Object[] items = null;
		int ix = -1;
		
		try {

			//������̔�r
			
			items = (Object[]) listShisaku.get(0);

			//�yQP@00342�z
//			//�̗p�T���v���m�n	no_sanpuru
//			if (!toString(reqData.getFieldVale("kihon", "rec", "no_sanpuru")).equals(
//					toString(items[3]))){
//				ret = false;
//			}
			
			//�H��@�S�����	cd_kaisya
			if (!toString(reqData.getFieldVale("kihon", "rec", "cd_kaisya")).equals(
					toString(items[4]))){
				ret = false;
			}
			//�H��@�S���H��	cd_kojyo
			if (!toString(reqData.getFieldVale("kihon", "rec", "cd_kojyo")).equals(
					toString(items[5]))){
				ret = false;
			}
			
			//�yQP@00342�z
//			//���萔	irisu
//			if (toDouble(reqData.getFieldVale("kihon", "rec", "irisu"))
//					!= toDouble(items[6])){
//				ret = false;
//			}
//			//�׎p	nisugata
//			if (!toString(reqData.getFieldVale("kihon", "rec", "nisugata")).equals(
//					toString(items[7]))){
//				ret = false;
//			}
//			//������]	kibo_genka
//			if (toDouble(reqData.getFieldVale("kihon", "rec", "kibo_genka"))
//					!= toDouble(items[8])){
//				ret = false;
//			}
//			//������]�P��CD	kibo_genka_cd_tani
//			if (!toString(reqData.getFieldVale("kihon", "rec", "kibo_genka_cd_tani")).equals(
//					toString(items[9]))){
//				ret = false;
//			}
//			//������]	kibo_baika
//			if (toDouble(reqData.getFieldVale("kihon", "rec", "kibo_baika"))
//					!= toDouble(items[10])){
//				ret = false;
//			}
//			
//			//�z�蕨��	butu_sotei
//			if (!toString(reqData.getFieldVale("kihon", "rec", "butu_sotei")).equals(
//					toString(items[19]))){
//				ret = false;
//			}
//			
//			//�̔�����	ziki_hanbai
//			if (!toString(reqData.getFieldVale("kihon", "rec", "ziki_hanbai")).equals(
//					toString(items[11]))){
//				ret = false;
//			}
//			//�v�攄��	keikaku_uriage
//			if (!toString(reqData.getFieldVale("kihon", "rec", "keikaku_uriage")).equals(
//					toString(items[12]))){
//				ret = false;
//			}
//			//�v�旘�v	keikaku_rieki
//			if (!toString(reqData.getFieldVale("kihon", "rec", "keikaku_rieki")).equals(
//					toString(items[13]))){
//				ret = false;
//			}
//			//�̔��㔄��	hanbaigo_uriage
//			if (!toString(reqData.getFieldVale("kihon", "rec", "hanbaigo_uriage")).equals(
//					toString(items[14]))){
//				ret = false;
//			}
//			//�̔��㗘�v	hanbaigo_rieki
//			if (!toString(reqData.getFieldVale("kihon", "rec", "hanbaigo_rieki")).equals(
//					toString(items[15]))){
//				ret = false;
//			}
			
			// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
			//�������b�g	seizo_roto
//			if (!toString(reqData.getFieldVale("kihon", "rec", "seizo_roto")).equals(
//					toString(items[16]))){
//				ret = false;
//			}
			// DEL 2013/7/2 shima�yQP@30151�zNo.37 end
			
			//�������Z����	memo_genkashisan
			if (!toString(reqData.getFieldVale("kihon", "rec", "memo_genkashisan")).equals(
					toString(items[17]))){
				ret = false;
			}
			//�v�Z���ځi�Œ��/�P�[�Xor�Œ��/kg�j	ragio_kesu_kg
			if (!toString(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg")).equals(
					toString(items[18]))){
				ret = false;
			}
			
			//�yQP@00342�z
			//�������Z�����i�c�ƘA���p�j	memo_genkashisan_eigyo
			if (!toString(reqData.getFieldVale("kihon", "rec", "memo_genkashisan_eigyo")).equals(
					toString(items[20]))){
				ret = false;
			}
			

			//������r

			for (int i = 0 ; i < reqData.getCntRow("genryo") ; i++){
				
				ix = seachGenryoIndex(
						reqData.getFieldVale("genryo", i, "cd_kotei")
						, reqData.getFieldVale("genryo", i, "seq_kotei"));
				if (ix > -1){
					
				}else{
					ret = false;
					break;
				}
				items = (Object[]) listGenryo.get(ix);

				//�H��CD	cd_kotei
				if (!toString(reqData.getFieldVale("genryo", i, "cd_kotei")).equals(
						toString(items[3]))){
					ret = false;
				}
				//�H��SEQ	seq_kotei
				if (!toString(reqData.getFieldVale("genryo", i, "seq_kotei")).equals(
						toString(items[4]))){
					ret = false;
				}
				//����CD	cd_genryo
				if (!toString(reqData.getFieldVale("genryo", i, "cd_genryo")).equals(
						toString(items[5]))){
					ret = false;
				}
				//�P��	tanka
				if (toDouble(reqData.getFieldVale("genryo", i, "tanka"))
						!= toDouble(items[6])){
					ret = false;
				}
				//����	budomari
				if (toDouble(reqData.getFieldVale("genryo", i, "budomari"))
						!= toDouble(items[7])){
					ret = false;
				}
				
			}

			//�����v�Z�i�T���v���j��r

			for (int i = 0 ; i < reqData.getCntRow("keisan") ; i++){
				
				ix = seachSanpuruIndex(
						reqData.getFieldVale("keisan", i, "seq_shisaku"));
				if (ix > -1){
					
				}else{
					ret = false;
					break;
				}
				items = (Object[]) listSanpuru.get(ix);
				
				//�yQP@00342�z���Z���~�̔�r
				if( !toString(reqData.getFieldVale("keisan", i, "fg_chusi")).equals( toString( items[11] ) ) ){
					ret = false;
				}
				
				//�yQP@00342�z���Z���~�̏ꍇ�̓`�F�b�N���Ȃ�
				if( toString(reqData.getFieldVale("keisan", i, "fg_chusi")).equals("1") ){
					
				}
				else{
					
					//����SEQ	seq_shisaku
					if (!toString(reqData.getFieldVale("keisan", i, "seq_shisaku")).equals(
							toString(items[3]))){
						ret = false;
					}
		
	//�y�V�T�N�C�b�N�i�����j�v�]�z���Z���̓��͕ύX�m�F���K��t�H�[�}�b�g�Ŕ��f�@TT.Nishigawa 2010/2/16 START -----------------------------------												
					//���Z��	shisan_date
					//DB�l��NULL�̏ꍇ
					if( toString(items[4]).equals("") ){
						//�������r
						if (!toString(reqData.getFieldVale("keisan", i, "shisan_date")).equals(
								toString(items[4]))){
							ret = false;
						}
					}
					//DB�l�����t�̏ꍇ
					else{
						//���t��r
						String sisanDate = toString(reqData.getFieldVale("keisan", i, "shisan_date"));
						DateFormat format = DateFormat.getDateInstance();
					    format.setLenient(false);
					    try {
					    	Date dtDb = format.parse(toString(items[4]));
					    	Date dtInput = format.parse(cnvDateFormat(sisanDate));
					    	
					        //���t�������ꍇ
					    	if(dtDb.compareTo(dtInput) == 0){
					    		
					    	}
					    	//���t���قȂ�ꍇ
					    	else{
					    		ret = false;
					    	}
					        
					    } catch (Exception e) {
					    	ret = false;
					    }
					}
	//�y�V�T�N�C�b�N�i�����j�v�]�z���Z���̓��͕ύX�m�F���K��t�H�[�}�b�g�Ŕ��f�@TT.Nishigawa 2010/2/16 END -----------------------------------																
					
					
					//�L�������i���j	yuuko_budomari
					if (toDouble(reqData.getFieldVale("keisan", i, "yuuko_budomari"))
							!= toDouble(items[5])){
						ret = false;
					}
					//���Ϗ[�U�ʁi���j	heikinjyutenryo
					if (toDouble(reqData.getFieldVale("keisan", i, "heikinjyutenryo"))
							!= toDouble(items[6])){
						ret = false;
					}
					
					String sen_koteihi = toString(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg"));
					if(sen_koteihi.equals("1")){
						//�Œ��/�P�[�X	kesu_kotehi
						if (toDouble(reqData.getFieldVale("keisan", i, "kesu_kotehi"))
								!= toDouble(items[7])){
							ret = false;
						}
						// ADD 2013/11/1 QP@30154 okano start
						//�Œ��/�P�[�X	kesu_kotehi
						if (toDouble(reqData.getFieldVale("keisan", i, "kesu_rieki"))
								!= toDouble(items[9])){
							ret = false;
						}
						// ADD 2013/11/1 QP@30154 okano end
					}
					else{
						//�Œ��/KG	kg_kotehi
						if (toDouble(reqData.getFieldVale("keisan", i, "kg_kotehi"))
								!= toDouble(items[8])){
							ret = false;
						}
						// ADD 2013/11/1 QP@30154 okano start
						//�Œ��/KG	kg_kotehi
						if (toDouble(reqData.getFieldVale("keisan", i, "kg_rieki"))
								!= toDouble(items[10])){
							ret = false;
						}
						// ADD 2013/11/1 QP@30154 okano end
					}
//ADD 2013/07/12 ogawa �yQP@30151�zNo.13 start
					//���ڌŒ�`�F�b�N	fg_koumokuchk
					if (toDouble(reqData.getFieldVale("keisan", i, "fg_koumokuchk"))
							!= toDouble(items[12])){
						ret = false;
					}
//ADD 2013/07/12 ogawa �yQP@30151�zNo.13 end
				}
			}
			

			//���ޔ�r

			for (int i = 0 ; i < reqData.getCntRow("shizai") ; i++){
				
				ix = seachShizaiIndex(
						reqData.getFieldVale("shizai", i, "seq_shizai"));
				if (ix > -1){
					
				}else{
					if (!toString(reqData.getFieldVale("shizai", i, "seq_shizai")).equals("")){
						ret = false;
					}else{
						
					}
					break;
				}
				items = (Object[]) listShizai.get(ix);

				//����SEQ	seq_shizai
				if (!toString(reqData.getFieldVale("shizai", i, "seq_shizai")).equals(
						toString(items[3]))){
					ret = false;
				}
				//���CD	cd_kaisya
				if (!toString(reqData.getFieldVale("shizai", i, "cd_kaisya")).equals(
						toString(items[4]))){
					ret = false;
				}
				//�H��CD	cd_kojyo
				if (!toString(reqData.getFieldVale("shizai", i, "cd_kojyo")).equals(
						toString(items[5]))){
					ret = false;
				}
				//����CD	cd_shizai
				if (toDouble(reqData.getFieldVale("shizai", i, "cd_shizai"))
						!= toDouble(items[6])){
					ret = false;
				}
				//���ޖ�	nm_shizai
				if (!toString(reqData.getFieldVale("shizai", i, "nm_shizai")).equals(
						toString(items[7]))){
					ret = false;
				}
				//�P��	tanka
				if (toDouble(reqData.getFieldVale("shizai", i, "tanka"))
						!= toDouble(items[8])){
					ret = false;
				}
				//�����i���j	budomari
				if (toDouble(reqData.getFieldVale("shizai", i, "budomari"))
						!= toDouble(items[9])){
					ret = false;
				}
				//�g�p��/���	shiyouryo
				if (toDouble(reqData.getFieldVale("shizai", i, "shiyouryo"))
						!= toDouble(items[10])){
					ret = false;
				}
				
			}
			
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			//��{���T�u
			
			for (int i = 0 ; i < reqData.getCntRow("kihonsub") ; i++){
				
				ix = seachKihonSubIndex(
						reqData.getFieldVale("kihonsub", i, "seq_shisaku"));
				if (ix > -1){
					
				}else{
					if (!toString(reqData.getFieldVale("kihonsub", i, "seq_shisaku")).equals("")){
						ret = false;
					}else{
						
					}
					break;
				}
				items = (Object[]) listKihonSub.get(ix);
				
				//���Z���~�̏ꍇ�̓`�F�b�N���Ȃ�
				if(toString(reqData.getFieldVale("keisan", i, "fg_chusi")).equals("1")){
				}else{
					
					//����SEQ	seq_shisaku
					if (!toString(reqData.getFieldVale("kihonsub", i, "seq_shisaku")).equals(
							toString(items[3]))){
						ret = false;
					}
					//�������b�g	seizo_roto
					if (toDouble(reqData.getFieldVale("kihonsub", i, "seizo_roto"))
							!= toDouble(items[4])){
						ret = false;
					}
				
				}
				
			}
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
		return ret;
	}
	/**
	 * DB���擾
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void getDBKomoku() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//SQL�o�b�t�@
		StringBuffer strSQL = new StringBuffer();
		
		try{

			//DB�R�l�N�V����
			createSearchDB();
			
			//�����񌟍�

			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  T310.cd_shain ");		//0
			strSQL.append(" ,T310.nen ");			//1
			strSQL.append(" ,T310.no_oi ");			//2
			strSQL.append(" ,T310.saiyo_sample ");	//3
			strSQL.append(" ,T310.cd_kaisha ");		//4
			strSQL.append(" ,T310.cd_kojo ");		//5
			strSQL.append(" ,T310.su_iri ");		//6
			strSQL.append(" ,T310.cd_nisugata ");	//7
			strSQL.append(" ,T310.genka ");			//8
			strSQL.append(" ,T310.cd_genka_tani ");	//9
			strSQL.append(" ,T310.baika ");			//10
			strSQL.append(" ,T310.dt_hatubai ");	//11
			strSQL.append(" ,T310.uriage_k ");		//12
			strSQL.append(" ,T310.rieki_k ");		//13
			strSQL.append(" ,T310.uriage_h ");		//14
			strSQL.append(" ,T310.rieki_h ");		//15
			strSQL.append(" ,T310.lot ");			//16

			//�yQP@00342�z
			strSQL.append(" ,T311.memo ");			//17

			strSQL.append(" ,T310.fg_keisan ");		//18
			strSQL.append(" ,T310.buturyo ");		//19
			//�yQP@00342�z
			strSQL.append(" ,T312.memo_eigyo ");		//20
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shisakuhin AS T310 ");
			
			//�yQP@00342�z
			strSQL.append(" LEFT JOIN tr_shisan_memo AS T311 ");
			strSQL.append(" ON T310.cd_shain = T311.cd_shain ");
			strSQL.append(" AND T310.nen = T311.nen ");
			strSQL.append(" AND T310.no_oi = T311.no_oi ");
			strSQL.append(" LEFT JOIN tr_shisan_memo_eigyo AS T312 ");
			strSQL.append(" ON T310.cd_shain = T312.cd_shain ");
			strSQL.append(" AND T310.nen = T312.nen ");
			strSQL.append(" AND T310.no_oi = T312.no_oi ");
			
			
			strSQL.append(" WHERE ");
			strSQL.append("     T310.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T310.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T310.no_oi     = " + toString(cd_shisaku_oi) + " ");
			
			//�yQP@00342�z
			strSQL.append(" AND T310.no_eda     = " + toString(cd_shisaku_eda) + " ");
			
			//DB����
			listShisaku = this.searchDB.dbSearch(strSQL.toString());
			
			//����ُ�񌟍�
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  T331.cd_shain ");		//0
			strSQL.append(" ,T331.nen ");			//1
			strSQL.append(" ,T331.no_oi ");			//2
			strSQL.append(" ,T331.seq_shisaku ");	//3
			strSQL.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) ");	//4
			strSQL.append(" ,T331.budomari ");		//5
			strSQL.append(" ,T331.heikinjuten ");	//6
			strSQL.append(" ,T331.cs_kotei ");		//7
			strSQL.append(" ,T331.kg_kotei ");		//8
			// ADD 2013/11/1 QP@30154 okano start
			strSQL.append(" ,T331.cs_rieki ");		//9
			strSQL.append(" ,T331.kg_rieki ");		//10
			// ADD 2013/11/1 QP@30154 okano end
			
			//�yQP@00342�z
			strSQL.append(" ,T331.fg_chusi ");		//11
//ADD 2013/07/12 ogawa �yQP@30151�zNo.13 start
			strSQL.append(" ,T331.fg_koumokuchk ");	//12
//ADD 2013/07/12 ogawa �yQP@30151�zNo.13 end
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shisaku AS T331 ");
			strSQL.append(" WHERE ");
			strSQL.append("     T331.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T331.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T331.no_oi     = " + toString(cd_shisaku_oi) + " ");
			
			//�yQP@00342�z
			strSQL.append(" AND T331.no_eda     = " + toString(cd_shisaku_eda) + " ");
			
			//DB����
			listSanpuru = this.searchDB.dbSearch(strSQL.toString());
			
			//�������
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  T320.cd_shain ");		//0
			strSQL.append(" ,T320.nen ");			//1
			strSQL.append(" ,T320.no_oi ");			//2
			strSQL.append(" ,T320.cd_kotei ");		//3
			strSQL.append(" ,T320.seq_kotei ");		//4
			strSQL.append(" ,T120.cd_genryo ");		//5
			strSQL.append(" ,ISNULL(T320.tanka_ins,T320.tanka_ma) AS tanka ");			//6
			strSQL.append(" ,ISNULL(T320.budomari_ins,T320.budomar_ma) AS budomari ");	//7
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_haigo AS T320 ");
			strSQL.append(" LEFT JOIN tr_haigo AS T120 ");
			strSQL.append(" ON  T320.cd_shain  = T120.cd_shain ");
			strSQL.append(" AND T320.nen       = T120.nen ");
			strSQL.append(" AND T320.no_oi     = T120.no_oi ");
			strSQL.append(" AND T320.cd_kotei  = T120.cd_kotei ");
			strSQL.append(" AND T320.seq_kotei = T120.seq_kotei ");
			strSQL.append(" WHERE ");
			strSQL.append("     T320.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T320.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T320.no_oi     = " + toString(cd_shisaku_oi) + " ");
			
			//�yQP@00342�z
			strSQL.append(" AND T320.no_eda     = " + toString(cd_shisaku_eda) + " ");
			
			//DB����
			listGenryo = this.searchDB.dbSearch(strSQL.toString());

			//���ޏ��
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  T340.cd_shain ");		//0
			strSQL.append(" ,T340.nen ");			//1
			strSQL.append(" ,T340.no_oi ");			//2
			strSQL.append(" ,T340.seq_shizai ");	//3
			strSQL.append(" ,T340.cd_kaisha ");		//4
			strSQL.append(" ,T340.cd_busho  ");		//5
			strSQL.append(" ,T340.cd_shizai ");		//6
			strSQL.append(" ,T340.nm_shizai ");		//7
			strSQL.append(" ,T340.tanka ");			//8
			strSQL.append(" ,T340.budomari ");		//9
			strSQL.append(" ,T340.cs_siyou ");		//10
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shizai AS T340 ");
			strSQL.append(" WHERE ");
			strSQL.append("     T340.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T340.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T340.no_oi     = " + toString(cd_shisaku_oi) + " ");
			
			//�yQP@00342�z
			strSQL.append(" AND T340.no_eda     = " + toString(cd_shisaku_eda) + " ");
			
			//DB����
			listShizai = this.searchDB.dbSearch(strSQL.toString());
			
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			//��{���T�u
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append(" T313.cd_shain, ");
			strSQL.append(" T313.nen, ");
			strSQL.append(" T313.no_oi, ");
			strSQL.append(" T313.seq_shisaku, ");
			strSQL.append(" T313.lot ");
			strSQL.append(" FROM ");
			strSQL.append(" tr_shisan_kihonjoho AS T313 ");
			strSQL.append(" WHERE ");
			strSQL.append(" T313.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T313.nen = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T313.no_oi = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T313.no_eda = " + toString(cd_shisaku_eda) + " ");
			
			//DB����
			listKihonSub = this.searchDB.dbSearch(strSQL.toString());
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B \nSQL:"
					+ strSQL.toString());
			
		}finally{
			//DB�R�l�N�V�����J��
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
				
			}
			//���[�J���ϐ��̊J��
			strSQL = null;

		}
		
	}
	/**
	 * �T���v�����ilistSanpuru�j���A�����ɍ��v����C���f�b�N�X�����o����
	 * @param seq_shisaku	�F����SEQ
	 * @param listGenryo	�F�������ʁi�T���v�����j
	 * @return int	�F�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachSanpuruIndex(
			
			  String seq_shisaku
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;
		
		try{

			for (int i = 0; i < listSanpuru.size(); i++){
				
				Object[] items = (Object[]) listSanpuru.get(i);
				
				//�z�����}�b�`���O
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_shisaku) == toInteger(items[3])
						){
					//�Y�������ꍇ�A�I��
					ret = i;
					break;

				}
				
			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
		
	}
	/**
	 * �������ilistGenryo�j���A�����ɍ��v����C���f�b�N�X�����o����
	 * @param cd_kotei		�F�H��CD
	 * @param seq_kotei		�F�H��SEQ
	 * @return int	�F�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachGenryoIndex(
			
			  String cd_kotei 
			, String seq_kotei
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;
		
		try{

			for (int i = 0; i < listGenryo.size(); i++){
				
				Object[] items = (Object[]) listGenryo.get(i);
				
				//�z�����}�b�`���O
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(cd_kotei) == toInteger(items[3])
					&&	toInteger(seq_kotei) == toInteger(items[4])
						){
					//�Y�������ꍇ�A�I��
					ret = i;
					break;

				}
				
			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
		
	}

	/**
	 * ���ޏ��ilistShizai�j���A�����ɍ��v����C���f�b�N�X�����o����
	 * @param seq_Shizai	�F����SEQ
	 * @return int	�F�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachShizaiIndex(
			
			  String seq_Shizai
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;
		
		try{

			for (int i = 0; i < listShizai.size(); i++){
				
				Object[] items = (Object[]) listShizai.get(i);
				
				//�z�����}�b�`���O
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_Shizai) == toInteger(items[3])
						){
					//�Y�������ꍇ�A�I��
					ret = i;
					break;

				}
				
			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
		
	}
	/**
	 * ��{�����ilistKihonSub�j���A�����ɍ��v����C���f�b�N�X�����o����
	 * @param seq_KihonSub	�F��{���SEQ
	 * @return int	�F�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachKihonSubIndex(
			
			  String seq_KihonSub
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;
		
		try{

			for (int i = 0; i < listKihonSub.size(); i++){
				
				Object[] items = (Object[]) listKihonSub.get(i);
				
				//�z�����}�b�`���O
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_KihonSub) == toInteger(items[3])
						){
					//�Y�������ꍇ�A�I��
					ret = i;
					break;

				}
				
			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
		
	}
	
}
