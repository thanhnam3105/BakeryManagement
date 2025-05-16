package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.math.BigDecimal;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Arrays;

/**
 *  �x�[�X�P���o�^
 *  : �x�[�X�P������o�^����B
 *  �@�\ID�FFGEN3530
 *
 * @author BRC Koizumi
 * @since  2016/09/07
 */
public class FGEN3530_Logic extends LogicBase{

	private String[] arryLot    = new String[30];
	private String[][] arryCost = new String[30][30];
	private String[] arrySort = new String[30];
	private String strLiteral2ndCd = "";	// ��񃊃e�����R�[�h

	/**
     * �x�[�X�P���o�^����
     * : �C���X�^���X����
     */
    public FGEN3530_Logic() {
        //���N���X�̃R���X�g���N�^
        super();

    }

    /**
     * �x�[�X�P���o�^
     *  : �x�[�X�P������o�^����B
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

        StringBuffer strSql = new StringBuffer();

        //���X�|���X�f�[�^�N���X
        RequestResponsKindBean resKind = null;

        try {

            //���X�|���X�f�[�^�i�@�\�j����
            resKind = new RequestResponsKindBean();

            sortData(reqData);

            strSql = createSQL(reqData);

            // �R�~�b�g
            execDB.Commit();

            // �@�\ID�̐ݒ�
            String strKinoId = reqData.getID();

            resKind.setID(strKinoId);

            // �e�[�u�����̐ݒ�
            String strTableNm = reqData.getTableID(0);

            resKind.addTableItem(strTableNm);

            // ���X�|���X�f�[�^�̌`��
            storageData(resKind.getTableItem(strTableNm));

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
            if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

            //�ϐ��̍폜
            strSql = null;
        }
        return resKind;
    }

    /**
     * �f�[�^�擾SQL�쐬
     * @param reqData�F���N�G�X�g�f�[�^
     * @param strSql�F��������SQL
     * @return strSql�F��������SQL
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private StringBuffer createSQL(

            RequestResponsKindBean reqData
            )
    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

        //SQL�i�[�p
        StringBuffer strSql = new StringBuffer();
        StringBuffer strSqlBuf = new StringBuffer();

        try {

            // �������[�h���擾����
            String strMode = toString(userInfoData.getMovement_condition().get(0));
            String strCdMaker    = reqData.getFieldVale("base_price_list", 0, "cd_maker");
            String strCdHouzai   = reqData.getFieldVale("base_price_list", 0, "cd_houzai");
            String strNoHansu    = reqData.getFieldVale("base_price_list", 0, "no_hansu");
            String strDtYuko     = reqData.getFieldVale("base_price_list", 0, "dt_yuko");
            String strChkKakunin = reqData.getFieldVale("base_price_list", 0, "chk_kakunin");
            String strChkShonin  = reqData.getFieldVale("base_price_list", 0, "chk_shonin");
            String strNameHansu    = reqData.getFieldVale("base_price_list", 0, "name_hansu");
            String strNameHouzai    = reqData.getFieldVale("base_price_list", 0, "name_houzai");
            String strChkMishiyo = reqData.getFieldVale("base_price_list", 0, "chk_mishiyo");		// ���g�p
            String strCategoryCd = reqData.getFieldVale("ma_literal", 0, "cd_category");	// �J�e�S���R�[�h

            String strBiko       = reqData.getFieldVale("base_price_list", 0, "biko");
            String strBiko_kojo       = "";

            // �V�K�܂��̓R�s�[�̏ꍇ�͕�ޖ��o�^�������s��
            if ((strMode.equals("1") || strMode.equals("3")) &&
            		strCdHouzai.equals("") && !strNameHouzai.equals("")) {
            	//�����敪�F�o�^�@���e�����f�[�^�o�^SQL�쐬�������ďo���B
            	strSqlBuf = literalKanriInsertSQL(reqData);

				strCdHouzai = strLiteral2ndCd;
            }

            if (strMode.equals("2")) {

                // ���g�p�`�F�b�N�L�̏ꍇ�A���e�����}�X�^�̖��g�p�t���O���X�V����
                if (!strChkMishiyo.equals("")) {

                	StringBuffer strUpdSql = new StringBuffer();

                	//SQL���̍쐬
                	strUpdSql.append("UPDATE ma_literal SET");
                	strUpdSql.append(" flg_mishiyo = '");
                	strUpdSql.append(strChkMishiyo);				// ���g�p
                	strUpdSql.append("' ,id_koshin = '");			// �X�V��
                	strUpdSql.append(userInfoData.getId_user());
                	strUpdSql.append("' ,dt_koshin = GETDATE()");	// �X�V���t
                	strUpdSql.append(" WHERE cd_category = '");
                	strUpdSql.append(strCategoryCd + "'");			// �J�e�S���R�[�h
                	strUpdSql.append(" AND cd_literal = '");
                	strUpdSql.append(strCdMaker + "'");				// ���e�����R�[�h�i���[�J�[�R�[�h�j
                	strUpdSql.append(" AND cd_2nd_literal = '");
                	strUpdSql.append(strCdHouzai + "'");			// ��񃊃e�����R�[�h�i��ރR�[�h�j

                	super.createExecDB();
                	execDB.BeginTran();
                	//SQL�����s
    				execDB.execSQL(strUpdSql.toString());
    				execDB.Commit();
                }
            }


            // �V�K�܂��̓R�s�[�̏ꍇ�͑��݃`�F�b�N���s��
            if (strMode.equals("1") || strMode.equals("3")) {

                strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_base_price_list   ");
                strSqlBuf.append("    ma_list   ");
                strSqlBuf.append("WHERE ");
                strSqlBuf.append("    cd_maker = '" + strCdMaker + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_houzai = '" + strCdHouzai + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    no_hansu = " + strNoHansu );

                super.createSearchDB();

                List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                if(lstRecset.size() > 0) {
                    em.ThrowException(ExceptionKind.���Exception,"E000336","","","");
                }
            }

            // �V�K�܂��̓R�s�[�̏ꍇ�͗L�����ő��݃`�F�b�N���s��
            if (strMode.equals("1") || strMode.equals("3")) {

                strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_base_price_list   ");
                strSqlBuf.append("    ma_list   ");
                strSqlBuf.append("WHERE ");
                strSqlBuf.append("    cd_maker = '" + strCdMaker + "' ");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_houzai = '" + strCdHouzai + "' ");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    CONVERT(VARCHAR, dt_yuko, 112) = '" + strDtYuko.replace("/", "") + "'");

                super.createSearchDB();

                List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                if(lstRecset.size() > 0) {
                    em.ThrowException(ExceptionKind.���Exception,"E000338","","","");
                }
            }

            if (strMode.equals("2")) {

                // ���F���͊m�F�ς݂ł��邱��
                if (!strChkShonin.equals("")) {

                    strSqlBuf = new StringBuffer();

                    strSqlBuf.append("SELECT ");
                    strSqlBuf.append("   * ");
                    strSqlBuf.append("FROM ma_base_price_list   ");
                    strSqlBuf.append("    ma_list   ");
                    strSqlBuf.append("WHERE ");
                    strSqlBuf.append("    id_kakunin IS NOT NULL ");
                    strSqlBuf.append("AND  ");
                    strSqlBuf.append("    cd_maker = '" + strCdMaker + "'");
                    strSqlBuf.append("AND  ");
                    strSqlBuf.append("    cd_houzai = '" + strCdHouzai + "'");
                    strSqlBuf.append("AND  ");
                    strSqlBuf.append("    no_hansu = " + strNoHansu );

                    super.createSearchDB();

                    List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                    if(lstRecset.size() <= 0) {
                        em.ThrowException(ExceptionKind.���Exception,"E000337" ,"","","");
                    }
                }
            }

            strSql.append("UPDATE ma_base_price_list SET  ");
            strSql.append("  dt_yuko    = CONVERT(DateTime, '" + strDtYuko +  "',111),  ");
            strSql.append("  biko       = '" + strBiko + "',  ");
            strSql.append("  biko_kojo       = '" + strBiko_kojo + "',  ");
            strSql.append("  name_houzai       = '" + strNameHouzai + "',  ");
            strSql.append("  name_hansu       = '" + strNameHansu + "',  ");

            // �X�V
            if (strMode.equals("2")) {
                if(!strChkKakunin.equals("")){
	                strSql.append("  id_kakunin  = " + userInfoData.getId_user() + ",  ");
	                strSql.append("  dt_kakunin  = GETDATE(),  ");
	            } else {
	                strSql.append("  id_kakunin  = NULL,  ");
	                strSql.append("  dt_kakunin  = NULL,  ");
	            }
            }

            // ���F�ς݂���������
            strSql.append("  id_shonin  = NULL,  ");
            strSql.append("  dt_shonin  = NULL,  ");

            strSql.append("  id_koshin  = " + userInfoData.getId_user() + ",  ");
            strSql.append("  dt_koshin  = GETDATE()  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            strSql.append("IF @@ROWCOUNT = 0    ");

            strSql.append("INSERT INTO ma_base_price_list (  ");
            strSql.append("  cd_maker,  ");
            strSql.append("  cd_houzai,  ");
            strSql.append("  no_hansu,  ");
            strSql.append("  dt_yuko,  ");

            if(!strNameHouzai.equals("")){
                strSql.append("  name_houzai,  ");
            }

            if(!strNameHansu.equals("")){
                strSql.append("  name_hansu,  ");
            }

            if(!strBiko.equals("")){
                strSql.append("  biko,  ");
            }

            if(!strBiko_kojo.equals("")){
                strSql.append("  biko_kojo,  ");
            }

            if(!strChkKakunin.equals("")){
                strSql.append("  id_kakunin,  ");
                strSql.append("  dt_kakunin,  ");
            }

            strSql.append("  id_toroku,  ");
            strSql.append("  dt_toroku,  ");
            strSql.append("  id_koshin,  ");
            strSql.append("  dt_koshin  ");
            strSql.append(")  ");
            strSql.append("VALUES  ");
            strSql.append("(  ");
            strSql.append("  '" + strCdMaker  + "',  ");
            strSql.append("  '" + strCdHouzai + "',  ");
            strSql.append("  "  + strNoHansu  + ",   ");
            strSql.append("  CONVERT(DateTime, '" + strDtYuko + "',111),  ");

            if(!strNameHouzai.equals("")){
            	strSql.append("  '" + strNameHouzai + "',   ");
            }

            if(!strNameHansu.equals("")){
            	strSql.append("  '" + strNameHansu + "',   ");
            }

            if(!strBiko.equals("")){
                strSql.append("  '" + strBiko + "',   ");
            }

            if(!strBiko_kojo.equals("")){
                strSql.append("  '" + strBiko_kojo + "',   ");
            }

            if(!strChkKakunin.equals("")){
                strSql.append("  " + userInfoData.getId_user() + ",  ");
                strSql.append("  GETDATE(),  ");
            }

            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE(),  ");
            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE()   ");
            strSql.append(")  ");

            super.createExecDB();
            execDB.BeginTran();
            execDB.execSQL(strSql.toString());

            // �폜
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_base_price  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            execDB.execSQL(strSql.toString());

            for (int i = 0; i < 31; i++ ) {

                strSql = new StringBuffer();

                strSql.append("INSERT INTO ma_base_price (  ");
                strSql.append("  cd_maker,  ");
                strSql.append("  cd_houzai, ");
                strSql.append("  no_hansu,  ");
                strSql.append("  no_row,    ");
                strSql.append("  nm_title, ");
                strSql.append("  no_value01, ");
                strSql.append("  no_value02, ");
                strSql.append("  no_value03, ");
                strSql.append("  no_value04, ");
                strSql.append("  no_value05, ");
                strSql.append("  no_value06, ");
                strSql.append("  no_value07, ");
                strSql.append("  no_value08, ");
                strSql.append("  no_value09, ");
                strSql.append("  no_value10, ");
                strSql.append("  no_value11, ");
                strSql.append("  no_value12, ");
                strSql.append("  no_value13, ");
                strSql.append("  no_value14, ");
                strSql.append("  no_value15, ");
                strSql.append("  no_value16, ");
                strSql.append("  no_value17, ");
                strSql.append("  no_value18, ");
                strSql.append("  no_value19, ");
                strSql.append("  no_value20, ");
                strSql.append("  no_value21, ");
                strSql.append("  no_value22, ");
                strSql.append("  no_value23, ");
                strSql.append("  no_value24, ");
                strSql.append("  no_value25, ");
                strSql.append("  no_value26, ");
                strSql.append("  no_value27, ");
                strSql.append("  no_value28, ");
                strSql.append("  no_value29, ");
                strSql.append("  no_value30  ");
                strSql.append(")  ");
                strSql.append("VALUES  ");
                strSql.append("(  ");
                strSql.append("  '" + strCdMaker  + "',  ");
                strSql.append("  '" + strCdHouzai + "',  ");
                strSql.append("  "  + strNoHansu  + ",   ");
                strSql.append("  "  + toString(i) + ",   ");

                String strTitle = reqData.getFieldVale("base_price", i, "nm_title");

                if(strTitle.equals("")){
                    strSql.append("  NULL,    ");
                } else {
                    strSql.append("  '"  + strTitle + "',   ");
                }

                String strValue;

                for(int j = 0; j < 30; j++){

                	if (i == 0) {
                		strValue = this.arryLot[j];
                	} else {
                		strValue = this.arryCost[i - 1][j];
                	}

                	if(strValue.equals("@")){
                        strSql.append("  NULL    ");
                    } else {
                    	strSql.append("  "  + strValue);
                    }

                    if(j != 29) {
                        strSql.append(" ,  ");
                    }
                }

                strSql.append(")  ");

                execDB.execSQL(strSql.toString());
            }

        } catch (Exception e) {
            this.em.ThrowException(e, "");
        } finally {

        }

        return strSql;
    }

    /**
     * �p�����[�^�[�i�[
     *  : �X�e�[�^�X������ʂւ̃��X�|���X�f�[�^�֊i�[����B
     * @param lstGenkaHeader : �������ʏ�񃊃X�g
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void storageData(
             RequestResponsTableBean resTable
            )
    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

        try {

            // �������ʂ̊i�[
            resTable.addFieldVale(0, "flg_return", "true");
            resTable.addFieldVale(0, "msg_error", "");
            resTable.addFieldVale(0, "no_errmsg", "");
            resTable.addFieldVale(0, "nm_class", "");
            resTable.addFieldVale(0, "cd_error", "");
            resTable.addFieldVale(0, "msg_system", "");

        } catch (Exception e) {

            this.em.ThrowException(e, "");

        } finally {

        }
    }

    /**
     * �x�[�X�P���l���\�[�g����
     *  : �x�[�X�P���l���s�Ɨ�Ń\�[�g����B
     * @param reqData : ���N�G�X�g�f�[�^
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void sortData(RequestResponsKindBean reqData)
    	    throws ExceptionSystem, ExceptionUser, ExceptionWaning
   {

        try {

        	// ���b�g�����i�[����ϐ�������������
        	Arrays.fill(this.arryLot, "@");

        	// �R�X�g���i�[����ϐ�������������
        	for (int x = 0; x < 30; x++) {
        		for (int y = 0; y < 30; y++) {
        			this.arryCost[x][y] = "@";
        		}
        	}

        	// ���b�g�����\�[�g�����̃n�b�V���𐶐�����
        	Map<String, Integer> hashMap = new HashMap<String, Integer>();

        	// ���b�g�����n�b�V���Ɋi�[����
        	for(int i = 0; i < 30; i++){
	    		String strKey = String.format("no_value%02d", (i + 1));
	    		String strValue = reqData.getFieldVale("base_price", 0, strKey);
	    		if (strValue.equals("")) {
	    			continue;
	    		} else {
	    			hashMap.put(strKey, Integer.parseInt(strValue));
	    		}
	    	}

	    	// List ���� (�\�[�g�p)
	        List<Map.Entry<String,Integer>> entries =
	              new ArrayList<Map.Entry<String,Integer>>(hashMap.entrySet());

	        Collections.sort(entries, new Comparator<Map.Entry<String,Integer>>() {

	            public int compare(
	                  Entry<String,Integer> entry1, Entry<String,Integer> entry2) {
	                return ((Integer)entry1.getValue()).compareTo((Integer)entry2.getValue());
	            }
	        });

	        // �n�b�V���ɓ���\�[�g�������b�g����z��Ɋi�[����
	        int col = 0;

	        for (Entry<String,Integer> s : entries) {
	        	this.arryLot[col] = s.getValue().toString();
	        	col++;
	        }

	        col = 0;

	        for (Entry<String,Integer> s : entries) {

	        	for(int row = 0; row < 30; row++){

	        		String strValue = reqData.getFieldVale("base_price", row + 1, s.getKey());

	        		if(strValue.equals("")){
	        			strValue = "@";
	        		} else {
	        			strValue = String.format("%.3f", new BigDecimal(strValue));
	        		}

	        		arryCost[row][col] = strValue;
	        	}

	            col++;
	        }

	        for(col = 0; col < 30; col++) {

	        	for (int y = 0; y < 30; y++) {
	    			this.arrySort[y] = "@";
	        	}

		        for(int row = 0, idx = 0; row < 30; row++) {
		        	if(!arryCost[row][col].equals("@")) {
		        		arrySort[idx++] = arryCost[row][col];
		        	}
		        }

		        for(int row = 0; row < 30; row++) {
		        	arryCost[row][col] = arrySort[row];
		        }
	        }

        } catch (Exception e) {

            this.em.ThrowException(e, "");

        } finally {

        }
    }

    /**
	 * ���e�����f�[�^�o�^SQL�쐬 : ���e�����f�[�^�o�^�p�@SQL���쐬���A�e�f�[�^��DB�ɓo�^����B
	 *
	 * @param reqData
	 *            �F���N�G�X�g�f�[�^
	 * @param strSql
	 *            �F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer literalKanriInsertSQL(RequestResponsKindBean requestData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {

			// �J�e�S���R�[�h
			String strCategoryCd = requestData.getFieldVale("ma_literal", 0, "cd_category");
			// ���e�����R�[�h
			String strLiteralCd = requestData.getFieldVale("ma_literal", 0, "cd_literal");
			// ���e������
			String strLiteralNm = requestData.getFieldVale("ma_literal", 0, "nm_literal");
			// �\���l
			String strSortNo = "";
			// ���l
			String strBiko = "";
			// �ҏW��
			String strFlgEdit = "1";

			// ��񃊃e������
			String strLiteral2ndNm = requestData.getFieldVale("ma_literal", 0, "nm_2nd_literal");
			// ��Q���e�����\���l
			String str2ndSortNo = "";

			// ���e�����f�[�^�o�^SQL�쐬
			strSql.append("INSERT INTO ma_literal (");
			strSql.append(" cd_category");
			strSql.append(" ,cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,value1");
			strSql.append(" ,value2");
			strSql.append(" ,no_sort");
			strSql.append(" ,biko");
			strSql.append(" ,flg_edit");
			strSql.append(" ,cd_group");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ,cd_2nd_literal");
			strSql.append(" ,nm_2nd_literal");
			strSql.append(" ,no_2nd_sort");
			strSql.append(" ,flg_2ndedit");
			strSql.append(" ,mail_address");
			strSql.append(" ,flg_mishiyo");
			strSql.append(" ) VALUES ( '");
			strSql.append(strCategoryCd + "'");

			StringBuffer strSelSql = new StringBuffer();
			StringBuffer strUpdSql = new StringBuffer();
			List<?> lstRecset = null;

			try {

				 super.createExecDB();
		         execDB.BeginTran();

				// �g�����U�N�V�����J�n
				super.createSearchDB();

				//��Q���e�����������͂���Ă���ꍇ�A��Q���e�����̓o�^

				/*** ��Q���e�����R�[�h�̎擾 start **************************************/
				// �̔ԏ���
				int intNoSeq = 0;

				// ���e�����R�[�h�̍̔�SQL�쐬
				strSelSql.append("SELECT top 1 cd_2nd_literal, no_2nd_sort, no_sort");
				strSelSql.append(" FROM ma_literal ");
				strSelSql.append(" WHERE cd_category = '" + strCategoryCd + "'");
				strSelSql.append(" AND cd_literal = '" + strLiteralCd + "'");
				strSelSql.append(" ORDER BY cd_2nd_literal desc ");
				//SQL�����s
				lstRecset = searchDB.dbSearch(strSelSql.toString());
				if (lstRecset.size() > 0) {
					// �̔Ԍ��ʂ�ޔ�

					Object[] items = (Object[]) lstRecset.get(0);
					// ��Q���e�����R�[�h
					if (items[0].toString().equals("")){
						intNoSeq = 0;
					}else{
						intNoSeq = Integer.parseInt(items[0].toString()); //001 �� 1
					}
					strLiteral2ndCd = String.valueOf(intNoSeq + 1);  //�{1���ĕ������

					// ��Q���e�����\����
					if(items[1] != null){
						if (items[1].toString().equals("")){
							intNoSeq = 0;
						}else{
							intNoSeq = Integer.parseInt(items[1].toString()); //001 �� 1
						}
					} else {
						intNoSeq = 0;
					}
					str2ndSortNo = String.valueOf(intNoSeq + 1);  //�{1���ĕ������
					strSortNo = items[2].toString();
				} else {
					strLiteral2ndCd = "1";
					str2ndSortNo = "1";
				}

				strSql.append(" ,'" + strLiteralCd + "'");  //���e�����R�[�h
				/*** ��Q���e�����R�[�h�̎擾 end **************************************/

				// ��Q���e�������g�p�J�n����܂ŁA�g���Ă����i��P�j���e�����p�̃��R�[�h���폜
				strUpdSql.append("DELETE ma_literal");
				strUpdSql.append(" WHERE cd_category = '");
				strUpdSql.append(strCategoryCd + "'");
				strUpdSql.append(" AND cd_literal = '");
				strUpdSql.append(strLiteralCd + "'");
				strUpdSql.append(" AND cd_2nd_literal = ''"); //��Q���e�������󔒂̂��̂��Ώ�

				execDB.execSQL(strUpdSql.toString());


			/*** ���e�������ȉ� **************************************/
			strSql.append(" ,'" + strLiteralNm + "'");  //���e������
			strSql.append(" ,NULL");  // �����ޒ��B���p�J�e�S���}�X�^�ł͕ҏW���Ȃ�����
			strSql.append(" ,NULL");  // �����ޒ��B���p�J�e�S���}�X�^�ł͕ҏW���Ȃ�����
			strSql.append(" ," + strSortNo);
			if ( !strBiko.equals("") ) {
				strSql.append(" ,'" + strBiko + "'");  // ���l
			} else {
				strSql.append(" ,NULL");
			}
			strSql.append(" ," + strFlgEdit);  // �ҏW�t���O
			strSql.append(" ,NULL");  // �����ޒ��B���p�J�e�S���}�X�^�ł͕ҏW���Ȃ�����
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");

			strLiteral2ndCd = ("000" + strLiteral2ndCd).substring(("000" + strLiteral2ndCd).length() - 3);
			//��Q���e�����������͂���Ă���ꍇ�A��Q���e�����̓o�^
			strSql.append(" ,'" + strLiteral2ndCd + "'");  //��Q���e�����R�[�h
			strSql.append(" ,'" + strLiteral2ndNm + "'");  //��Q���e������
			strSql.append(" ,'" + str2ndSortNo + "'");  //��Q���e�����\����
			strSql.append(" ,'1'");  //��Q���e�����g�p�t���O
				strSql.append(" ,NULL");  //���[���A�h���X
				strSql.append(" ,'0'");  //���g�p�t���O

			strSql.append(")");

				execDB.execSQL(strSql.toString());		// SQL���s

				//�R�~�b�g
				execDB.Commit();

			} catch (Exception e) {
				if (execDB != null) {
					// ���[���o�b�N
					execDB.Rollback();
				}

				this.em.ThrowException(e, "");
			} finally {
				//���X�g�̔j��
				removeList(lstRecset);
				if (searchDB != null) {
					// �Z�b�V�����̃N���[�Y
					searchDB.Close();
					searchDB = null;
				}
				if (execDB != null) {
					// �Z�b�V�����̃N���[�Y
					execDB.Close();
					execDB = null;
				}
				//�ϐ��̍폜
				strSelSql = null;
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
