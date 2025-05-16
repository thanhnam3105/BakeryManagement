package jp.co.blueflag.shisaquick.srv.commonlogic_genka;

import java.util.List;
import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �����̐�����ЁA�����H��̐􂢑ւ�
 *  : ���얈�̐􂢑ւ�
 * @author isono
 * @since  2009/10/21
 * 
 */
public class CGEN0010_Logic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public CGEN0010_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���Z�ް������􂢑ւ��̎���
	 * @param cd_shain	�F����CD�Ј�
	 * @param nen		�F����CD�N
	 * @param no_oi		�F����CD�ǔ�
	 * @param cd_kaisha	�F�V���CD
	 * @param cd_kojo	�F�V�H��CD
	 * @param no_eda	�F�yQP@00342�z�}��
	 * @param userInfo	�F���[�U�[���
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void ExecLogic(

			BigDecimal          cd_shain
			,int                nen
			,int                no_oi
			,int                cd_kaisha
			,int                cd_kojo
			,int                no_eda
			,UserInfoData       userInfo
			
			) 

	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = userInfo;
		//SQL�o�b�t�@
		StringBuffer strSQL = new StringBuffer();
		//�������ʃo�b�t�@
		List<?> lstSearchAry = null;
		
		try{

			//DB�R�l�N�V��������
			super.createExecDB();								
			super.createSearchDB();
			
			//�g�����U�N�V�����J�n
			this.searchDB.BeginTran();
			execDB.setSession(searchDB.getSession());
			
			//�yQP@00342�z����SQL����
			strSQL = makeSearchSQL(cd_shain, nen, no_oi, cd_kaisha, cd_kojo, no_eda);
			
			//DB����
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

			strSQL = null;
			//�yQP@00342�z�X�VSQL�����i���Z�@����ið��ٍX�V�j
			strSQL = makeExecSQL_T310(cd_shain, nen, no_oi, cd_kaisha, cd_kojo,no_eda);

			//DB�X�V�i���Z�@����ið��ٍX�V�j
			this.execDB.execSQL(strSQL.toString());
			
			if ( lstSearchAry.size() >= 0 ) {
				
				for (int i = 0; i < lstSearchAry.size(); i++) {

					strSQL = null;
					Object[] items = (Object[]) lstSearchAry.get(i);
					
					//�yQP@00342�z�X�VSQL����
					strSQL = makeExecSQL(items,no_eda);
					
					if (strSQL == null){
						//�􂢑ւ��ΏۊO�̏ꍇ�́AstrSQL��null�ŋA���Ă��܂��B
						//SKIP�I
						
					}else{
						//DB�X�V
						this.execDB.execSQL(strSQL.toString());
						
					}
					
				}
				
			}
			
			//DB�R�~�b�g
			this.searchDB.Commit();
			
		}catch(Exception e){
			//DB���[���o�b�N
			this.searchDB.Rollback();

			//��O�̃X���[
			this.em.ThrowException(e, "�����̐�����ЁA�����H��̐􂢑ւ��Ɏ��s���܂����B");
			
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
		
	}

	/**
	 * �􂢑ւ������f�[�^�����pSQL�𐶐�����B
	 * @param cd_shain	�F����CD�Ј�
	 * @param nen		�F����CD�N
	 * @param no_oi		�F����CD�ǔ�
	 * @param no_eda	�F�yQP@00342�z����CD�}��
	 * @param cd_kaisha	�F�V���CD
	 * @param cd_kojo	�F�V�H��CD
	 * @return�@StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL(

			BigDecimal			cd_shain
			,int                nen
			,int                no_oi
			,int                cd_kaisha
			,int                cd_kojo
			,int                no_eda
			)
	
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQL�o�b�t�@
		StringBuffer ret = new StringBuffer();
		
		try{
			ret.append(" SELECT ");
			ret.append("  T310.cd_shain AS cd_shain ");
			ret.append(" ,T310.nen AS nen ");
			ret.append(" ,T310.no_oi AS no_oi ");
			ret.append(" ,T310.cd_kaisha AS cd_kaisha_SHISAN ");
			ret.append(" ,T310.cd_kojo AS cd_kojo_SHISAN ");
			ret.append(" ," + toString(cd_kaisha) + " AS cd_kaisha_NEW ");
			ret.append(" ," + toString(cd_kojo) + " AS cd_kojo_NEW ");
			ret.append(" ,T120.cd_genryo AS cd_genryo ");
			
			
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@START
//			ret.append(" ,T120.nm_genryo AS nm_genryo_SHISAKU ");
//			ret.append(" ,ISNULL(M993.name_hinmei,T120.nm_genryo) AS nm_genryo_SHISAKU ");
			ret.append(" ,M993.name_hinmei AS nm_genryo_SHISAKU ");
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@END
			
			
			ret.append(" ,T320.tanka_ins AS tanka_SHISAN_ins ");
			ret.append(" ,T320.budomari_ins AS budomari_SHISAN_ins ");
			ret.append(" ,T320.tanka_ma AS tanka_SHISAN_ma ");
			ret.append(" ,T320.budomar_ma AS budomar_SHISAN_ma ");
			ret.append(" ,T402.tanka AS tanka_MASTA ");
			ret.append(" ,T402.budomari AS budomari_MASTA ");
			ret.append(" ,T402_2.tanka AS tanka_MAX ");
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No14�z�P���E�����̉�ЁE�H��R�[�h�擾�@TT.NISHIGAWA�@START	
//			ret.append(" ,T402_2.budomari AS budomari_MIN ");
			ret.append(" ,T402_3.budomari AS budomari_MIN ");
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No14�z�P���E�����̉�ЁE�H��R�[�h�擾�@TT.NISHIGAWA�@END
			ret.append(" ,T120.tanka AS tanka_SHISAKU ");
			ret.append(" ,T120.budomari AS budomari_SHISAKU ");
			ret.append(" ,T320.cd_kotei AS cd_kotei ");
			ret.append(" ,T320.seq_kotei AS seq_kotei ");
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No14�z�P���E�����̉�ЁE�H��R�[�h�擾�@TT.NISHIGAWA�@START
			ret.append(" ,T402_2.cd_kaisha AS cd_kaisha_MAX_TANKA ");
			ret.append(" ,T402_2.cd_busho AS cd_busho_MAX_TANKA ");
			ret.append(" ,T402_3.cd_kaisha AS cd_kaisha_MIN_BUDOMARI ");
			ret.append(" ,T402_3.cd_busho AS cd_busho_MIN_BUDOMARI ");
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No14�z�P���E�����̉�ЁE�H��R�[�h�擾�@TT.NISHIGAWA�@END
			
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@START			
			//�V�K�����p�Ɏ擾
			ret.append(" ,T120.nm_genryo AS nm_genryo_N ");
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@END
			
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
			ret.append(" ,genryokojo.flg_shiyo AS flg_shiyo "); //26
//add end   -------------------------------------------------------------------------------
			
			ret.append(" FROM tr_shisan_shisakuhin AS T310 ");
			ret.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			ret.append("        ON T310.cd_shain = T320.cd_shain  ");
			ret.append("       AND T310.nen      = T320.nen  ");
			ret.append("       AND T310.no_oi    = T320.no_oi  ");
			
			//�yQP@00342�z
			ret.append("       AND T310.no_eda    = T320.no_eda  ");
			
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append("        ON T320.cd_shain = T120.cd_shain  ");
			ret.append("       AND T320.nen      = T120.nen  ");
			ret.append("       AND T320.no_oi    = T120.no_oi  ");
			ret.append("       AND T320.cd_kotei = T120.cd_kotei ");
			ret.append("       AND T320.seq_kotei= T120.seq_kotei ");
			
			
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@START
			ret.append(" LEFT JOIN ma_Hinmei_Mst AS M993 ");
			ret.append("       ON M993.cd_kaisha = " +toString(cd_kaisha) );
			ret.append("       AND M993.cd_hinmei = T120.cd_genryo ");
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@END			
			
			
			
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�P���E�����̎擾���@�ύX�@TT.NISHIGAWA�@START
//			ret.append(" LEFT JOIN ma_genryokojo AS T402 ");
//			ret.append("        ON " + toString(cd_kaisha) + " = T402.cd_kaisha  ");
//			ret.append("       AND " + toString(cd_kojo) + " = T402.cd_busho   ");
//			ret.append("       AND T120.cd_genryo = T402.cd_genryo  ");
			ret.append(" LEFT JOIN ma_HinTB_Mst AS T402 ");
			ret.append("        ON " + toString(cd_kaisha) + " = T402.cd_kaisha  ");
			ret.append("       AND " + toString(cd_kojo) + " = T402.cd_busho   ");
			ret.append("       AND RIGHT('00000000000'+T402.cd_hinmei,LEN(T120.cd_genryo)) = T120.cd_genryo  ");
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�P���E�����̎擾���@�ύX�@TT.NISHIGAWA�@END
			
			
			
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No14�z�P���E�����̉�ЁE�H��R�[�h�擾�@TT.NISHIGAWA�@START	
//			ret.append(" LEFT JOIN ( ");
//			ret.append("      SELECT  ");
//			ret.append("               T402.cd_kaisha AS cd_kaisha ");
//			ret.append("              ,T402.cd_genryo AS cd_genryo ");
//			ret.append("              ,MAX(T402.tanka) AS tanka ");
//			ret.append("              ,MIN(T402.budomari) AS budomari ");
//			ret.append("      FROM ma_genryokojo AS T402 ");
//			ret.append("      GROUP BY T402.cd_kaisha ");
//			ret.append("              ,T402.cd_genryo  ");
//			ret.append(" ) AS T402_2 ");
//			ret.append("        ON " + toString(cd_kaisha) + " = T402_2.cd_kaisha ");
//			ret.append("       AND T120.cd_genryo = T402_2.cd_genryo ");

			ret.append(" OUTER APPLY  ");
			ret.append("   (SELECT TOP 1 ");
			ret.append("    T402_T.cd_kaisha AS cd_kaisha ");
			ret.append("   ,T402_T.cd_busho AS cd_busho  ");
			ret.append("   ,T402_T.cd_hinmei AS cd_genryo ");
			ret.append("   ,T402_T.tanka AS tanka ");
			ret.append("   FROM ma_HinTB_Mst AS T402_T ");
			ret.append("   WHERE  ");
			ret.append("        " + toString(cd_kaisha) + " = T402_T.cd_kaisha ");
			ret.append("   AND RIGHT('00000000000'+T402_T.cd_hinmei,LEN(T120.cd_genryo)) = T120.cd_genryo ");
			ret.append("   ORDER BY T402_T.cd_kaisha ");
			ret.append("         ,  T402_T.cd_hinmei ");
			ret.append("         ,  T402_T.tanka desc ");
			ret.append("    ) AS T402_2 ");
			
			ret.append(" OUTER APPLY  ");
			ret.append("   (SELECT TOP 1 ");
			ret.append("    T402_B.cd_kaisha AS cd_kaisha ");
			ret.append("   ,T402_B.cd_busho AS cd_busho  ");
			ret.append("   ,T402_B.cd_hinmei AS cd_genryo ");
			ret.append("   ,T402_B.budomari AS budomari ");
			ret.append("   FROM ma_HinTB_Mst AS T402_B ");
			ret.append("   WHERE  ");
			ret.append("        " + toString(cd_kaisha) + " = T402_B.cd_kaisha ");
			ret.append("   AND RIGHT('00000000000'+T402_B.cd_hinmei,LEN(T120.cd_genryo)) = T120.cd_genryo ");
			ret.append("   ORDER BY T402_B.cd_kaisha ");
			ret.append("         ,  T402_B.cd_hinmei ");
			ret.append("         ,  T402_B.budomari ");
			ret.append("   ) AS T402_3 ");
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No14�z�P���E�����̉�ЁE�H��R�[�h�擾�@TT.NISHIGAWA�@END
			
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
			ret.append(" LEFT JOIN ma_genryokojo AS genryokojo ");
			ret.append(" ON " + toString(cd_kaisha) + " = genryokojo.cd_kaisha ");
			ret.append(" AND " + toString(cd_kojo) + " = genryokojo.cd_busho ");
			ret.append(" AND T120.cd_genryo = genryokojo.cd_genryo ");
//add end   -------------------------------------------------------------------------------
			
			ret.append(" WHERE ");
			ret.append("       T310.cd_shain = " + toString(cd_shain) + " ");
			ret.append("   AND T310.nen      = " + toString(nen) + " ");
			ret.append("   AND T310.no_oi    = " + toString(no_oi) + " ");
			
			//�yQP@00342�z
			ret.append("   AND T310.no_eda    = " + toString(no_eda) + " ");
			
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "����SQL�̐����Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * ���Z�@����ið��ق̉��/�H����X�V����SQL�𐶐�
	 * @param cd_shain	�F����CD�Ј�
	 * @param nen		�F����CD�N
	 * @param no_oi		�F����CD�ǔ�
	 * @param no_eda	�F�yQP@00342�z����CD�}��
	 * @param cd_kaisha	�F�V���CD
	 * @param cd_kojo	�F�V�H��CD
	 * @return
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T310(
			
			BigDecimal             	cd_shain
			,int                nen
			,int                no_oi
			,int                cd_kaisha
			,int                cd_kojo
			,int                no_eda
			)
	
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();
		
		try{
			
			ret.append(" UPDATE tr_shisan_shisakuhin ");
			ret.append(" SET ");
			ret.append("     cd_kaisha = " + cd_kaisha + " ");
			ret.append("    ,cd_kojo   = " + cd_kojo + " ");
			ret.append("    ,id_koshin = " + userInfoData.getId_user() + " ");
			ret.append("    ,dt_koshin = GETDATE() ");
			ret.append(" WHERE ");
			ret.append("     cd_shain = " + cd_shain + " ");
			ret.append(" AND nen      = " + nen + " ");
			ret.append(" AND no_oi    = " + no_oi + " ");
			
			//�yQP@00342�z
			ret.append(" AND no_eda    = " + no_eda + " ");
			
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * �􂢑ւ����sSQL�𐶐�����
	 * @param items		�F�􂢑ւ������f�[�^1����
	 * @param no_eda	�F�yQP@00342�z����CD�}��
	 * @return�@StringBuffer	�FSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL(
			
			Object[] items
			,int no_eda
			)
	
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();
		
		try{
			
			//�����̎��W
			//����CD
			String strGenryoCD = toString(items[7]);
			//������
			String strGenryoNm = toString(items[8]);
			
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@START
			//N������
			String strNGenryoNm = toString(items[25]);
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@END
			

//2010/02/15 ADD TT.ISONO START MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
			//���CD MAX�P��
			String cd_kaisha_MAX_TANKA = toString(items[5]);
			//�H��CD MAX�P��
			String cd_kojo_MAX_TANKA = toString(items[6]);
			//���CD MIN����
			String cd_kaisha_MIN_BUDOMARI = toString(items[5]);
			//�H��CD MIN����
			String cd_kojo_MIN_BUDOMARI = toString(items[6]);
//2010/02/15 ADD TT.ISONO END   MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
			
			
			//����������̏ꍇ
			if(strGenryoNm.equals("")){
				
			}
			//����������łȂ��ꍇ
			else{
				//�����L���폜
				if(strGenryoNm.substring(0, 1).equals("��") || 
						strGenryoNm.substring(0, 1).equals("��")){
					
					//���L���폜
					strGenryoNm = strGenryoNm.substring(1);
				}
				
			}
			
// 2009/11/10 �j�� isono
//			//�P���@�������Z�i���́j
//			String strTanka_Ins = toString(items[9],"null");
//			//�����@�������Z�i���́j
//			String strBudomari_Ins = toString(items[10],"null");
			//�P���@�������Z�i�}�X�^�j
			//String strTanka_Mas = toString(items[11]);
			//�����@�������Z�i�}�X�^�j
			//String strBudomari_Mas = toString(items[12]);
			//�P���@�������
			String strTanka_Shisaku = toString(items[17],"null");
			//�����@�������
			String strBudomari_Shisaku = toString(items[18],"null");
			//�P���@�H�ꌴ���}�X�^
			String strTanka_KOUZYO_MAS = toString(items[13],"null");
			//�����@�H�ꌴ���}�X�^
			String strBudomari_KOUZYO_MAS = toString(items[14],"null");
			//�P���@��Г�MAX
			String strTanka_MAX = toString(items[15],"null");
			//�����@��Г�MIN
			String strBudomari_MIN = toString(items[16],"null");
			//����CD�@�Ј�
			String strCd_shain = toString(items[0]);
			//����CD�@�N
			String strNen = toString(items[1]);
			//����CD�@�ǔ�
			String strNo_oi = toString(items[2]);
			//�H��CD
			String strCd_kotei = toString(items[19]);
			//�H��SEQ
			String strSeq_kotei = toString(items[20]);
			
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//�g�p����
			String strFg_Shiyo = toString(items[26]);
//add end   -------------------------------------------------------------------------------
			
			//�ҏW���ڗp�o�b�t�@
			//������
			String strGenryo = "";
			//�P��
			String strTanka = "";
			//����
			String strBudomari = "";
			
			//�ҏW���e����
// 2009/11/10 �j�� isono
//			//6�@���Z�@�z���e�[�u���̒P���i���́j�A�����i���́j���ݒ肳��Ă���ꍇ
//			if(strTanka_Ins.equals("null") && strBudomari_Ins.equals("null")){
//				
//			}else{
//				ret = null;
//				return ret;
//			}
			
			if (!strGenryoCD.equals("") && strGenryoCD.substring(0, 1).equals( "N")){
				//5 ����CD�̐擪�ɁhN�h�i�V�K��������فj���ݒ肳��Ă���ꍇ

//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@START
				//������
				//strGenryo = strGenryoNm;
				
				//������
				strGenryo = strNGenryoNm;
//2010/05/12�@�V�T�N�C�b�N�i�����j�v�]�y�Č�No3�z�������̎擾���@�ύX�@TT.NISHIGAWA�@END
				
				//�P��
				strTanka = strTanka_Shisaku;
				//����
				strBudomari = strBudomari_Shisaku;

//2010/02/15 UPDATE TT.ISONO START MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
				//���CD MAX�P��
				cd_kaisha_MAX_TANKA = toString("null");
				//�H��CD MAX�P��
				cd_kojo_MAX_TANKA = toString("null");
				//���CD MIN����
				cd_kaisha_MIN_BUDOMARI = toString("null");
				//�H��CD MIN����
				cd_kojo_MIN_BUDOMARI = toString("null");
//2010/02/15 UPDATE TT.ISONO END   MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
				
			}else if(!strBudomari_KOUZYO_MAS.equals("null") || !strBudomari_KOUZYO_MAS.equals("null")){
				//1 �V���CD�A�V�H��CD�A����CD�ɊY������}�X�^�i�H�ꌴ���}�X�^�j�����݂���ꍇ
				
				//�P��
				strTanka = strTanka_KOUZYO_MAS;
				//����
				strBudomari = strBudomari_KOUZYO_MAS;

//2010/02/15 UPDATE TT.ISONO START MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
				//���CD MAX�P��
				cd_kaisha_MAX_TANKA = toString(items[5]);
				//�H��CD MAX�P��
				cd_kojo_MAX_TANKA = toString(items[6]);
				//���CD MIN����
				cd_kaisha_MIN_BUDOMARI = toString(items[5]);
				//�H��CD MIN����
				cd_kojo_MIN_BUDOMARI = toString(items[6]);
//2010/02/15 UPDATE TT.ISONO END   MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
								
			}else if(strBudomari_KOUZYO_MAS.equals("null") && strBudomari_KOUZYO_MAS.equals("null")){
				//2 �V���CD�A�V�H��CD�A����CD�ɊY������}�X�^�i�H�ꌴ���}�X�^�j�����݂��Ȃ��ꍇ
				
				if (!strTanka_MAX.equals("null") || !strBudomari_MIN.equals("null")){
					//3 �V���CD�A����CD�ɊY������}�X�^�i�H�ꌴ���}�X�^�j�����݂���ꍇ
					
					//������
					strGenryoNm = "��" + strGenryoNm;
					//�P��
					strTanka = strTanka_MAX;
					//����
					strBudomari = strBudomari_MIN;
					
//2010/02/15 UPDATE TT.ISONO START MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
					//���CD MAX�P��
					cd_kaisha_MAX_TANKA = toString(items[21]);
					//�H��CD MAX�P��
					cd_kojo_MAX_TANKA = toString(items[22]);
					//���CD MIN����
					cd_kaisha_MIN_BUDOMARI = toString(items[23]);
					//�H��CD MIN����
					cd_kojo_MIN_BUDOMARI = toString(items[24]);
//2010/02/15 UPDATE TT.ISONO END   MAX�P���AMIN�����@��ЁA�H��̒ǉ�			

				}else {
					//4 �V���CD�A����CD�ɊY������}�X�^�i�H�ꌴ���}�X�^�j�����݂��Ȃ��ꍇ
					
					//������
					//strGenryo = "��" + "";
					strGenryo = toString("");
					
					//�P��
					strTanka = toString("null");
					//����
					strBudomari = toString("null");
					
//2010/02/15 UPDATE TT.ISONO START MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
					//���CD MAX�P��
					cd_kaisha_MAX_TANKA = toString("null");
					//�H��CD MAX�P��
					cd_kojo_MAX_TANKA = toString("null");
					//���CD MIN����
					cd_kaisha_MIN_BUDOMARI = toString("null");
					//�H��CD MIN����
					cd_kojo_MIN_BUDOMARI = toString("null");
//2010/02/15 UPDATE TT.ISONO END   MAX�P���AMIN�����@��ЁA�H��̒ǉ�			

				}
				
			}
			
//add start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			
			if (!strGenryoCD.equals("") && strGenryoCD.substring(0, 1).equals( "N")){
				
			}
			else{
				
				//3�����g�p���т��������=1
				if(strFg_Shiyo.equals("1")){
					strGenryo = strGenryoNm;
				}
				//3�����g�p���т��Ȃ�����=0
				else if(strFg_Shiyo.equals("0")){
					//�����L���폜
					if(!strGenryoNm.equals("")){
						if(strGenryoNm.substring(0, 1).equals("��") || 
								strGenryoNm.substring(0, 1).equals("��")){
							//���L���폜
							strGenryoNm = strGenryoNm.substring(1);
						}
					}
					strGenryo ="��" + strGenryoNm;
				}
				//��L�ȊO=null
				else{
					if (strGenryoNm.equals("")){
						strGenryo = strGenryoNm;
					}
					else{
						//�����L���폜
						if(strGenryoNm.substring(0, 1).equals("��") || 
								strGenryoNm.substring(0, 1).equals("��")){
							//���L���폜
							strGenryoNm = strGenryoNm.substring(1);
						}
						strGenryo = "��" + strGenryoNm;
					}
				}	
			}
//add end ---------------------------------------------------------------------------------
			
			//���Z�@�z��ð��فiT320�j�X�V�pSQL����
			ret.append(" UPDATE tr_shisan_haigo ");
			ret.append(" SET ");
			ret.append("     nm_genryo = '" + strGenryo + "' ");
//			ret.append("    ,tanka_ins = null ");
//			ret.append("    ,budomari_ins= null ");
			ret.append("    ,tanka_ma  = " + strTanka + " ");
			ret.append("    ,budomar_ma= " + strBudomari + " ");
//2010/02/15 UPDATE TT.ISONO START MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
			ret.append("    ,cd_kaisha_tanka = " + cd_kaisha_MAX_TANKA + " ");
			ret.append("    ,cd_kojo_tanka = " + cd_kojo_MAX_TANKA + " ");
			ret.append("    ,cd_kaisha_budomari = " + cd_kaisha_MIN_BUDOMARI + " ");
			ret.append("    ,cd_kojo_budomari = " + cd_kojo_MIN_BUDOMARI + " ");
//2010/02/15 UPDATE TT.ISONO END   MAX�P���AMIN�����@��ЁA�H��̒ǉ�			
			ret.append("    ,id_koshin = " + userInfoData.getId_user() + " ");
			ret.append("    ,dt_koshin = getdate() ");
			ret.append(" WHERE ");
			ret.append("      cd_shain  = " + strCd_shain + " ");
			ret.append("  AND nen       = " + strNen + " ");
			ret.append("  AND no_oi     = " + strNo_oi + " ");
			
			//�yQP@00342�z
			ret.append("  AND no_eda     = " + no_eda + " ");
			
			ret.append("  AND cd_kotei  = " + strCd_kotei + " ");
			ret.append("  AND seq_kotei = " + strSeq_kotei + " ");

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�􂢑ւ����sSQL�𐶐��Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;

	}
	
}
