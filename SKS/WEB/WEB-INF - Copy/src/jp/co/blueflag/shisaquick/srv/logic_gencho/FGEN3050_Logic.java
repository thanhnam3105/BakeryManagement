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
 * �yQP@30297�z
 *  �R�X�g�e�[�u���o�^
 *  : �R�X�g�e�[�u������o�^����B
 *  �@�\ID�FFGEN3040
 *
 * @author Sakamoto
 * @since  2014/01/25
 */
public class FGEN3050_Logic extends LogicBase{

	private String[] arryLot    = new String[30];
	private String[][] arryCost = new String[30][30];
	private String[] arrySort = new String[30];

	/**
     * �R�X�g�e�[�u���o�^����
     * : �C���X�^���X����
     */
    public FGEN3050_Logic() {
        //���N���X�̃R���X�g���N�^
        super();

    }

    /**
     * �R�X�g�e�[�u���o�^
     *  : �R�X�g�e�[�u������o�^����B
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

        try {

            // �������[�h���擾����
            String strMode = toString(userInfoData.getMovement_condition().get(0));
            String strCdMaker    = reqData.getFieldVale("cost_list", 0, "cd_maker");
            String strCdHouzai   = reqData.getFieldVale("cost_list", 0, "cd_houzai");
            String strNoHansu    = reqData.getFieldVale("cost_list", 0, "no_hansu");
            String strDtYuko     = reqData.getFieldVale("cost_list", 0, "dt_yuko");
            String strBiko       = reqData.getFieldVale("cost_list", 0, "biko");
            String strChkKakunin = reqData.getFieldVale("cost_list", 0, "chk_kakunin");
            String strChkShonin  = reqData.getFieldVale("cost_list", 0, "chk_shonin");
            String strBiko_kojo       = reqData.getFieldVale("cost_list", 0, "biko_kojo");

// �yKPX@1602367�z add start
            String strNameHouzai   = reqData.getFieldVale("cost_list", 0, "name_houzai");
            String strNameHansu    = reqData.getFieldVale("cost_list", 0, "name_hansu");
            String strChkMishiyo = reqData.getFieldVale("cost_list", 0, "chk_mishiyo");		// ���g�p
            String strCategoryCd = reqData.getFieldVale("cost_list", 0, "cd_category");	// �J�e�S���R�[�h
            String strNoBaseHansu    = reqData.getFieldVale("cost_list", 0, "no_basehansu");	// �x�[�X�Ő�

            if (strMode.equals("2")) {
                // �o�^�i�X�V�j���A���e�����}�X�^�̖��g�p�t���O���X�V����
            	// 1:���g�p 0:�g�p
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

                	//SQL�����s
                	super.createExecDB();
                	execDB.BeginTran();
                	//SQL�����s
    				execDB.execSQL(strUpdSql.toString());
    				execDB.Commit();
                }
            }
// �yKPX@1602367�z add end

            // �V�K�܂��̓R�s�[�̏ꍇ�͑��݃`�F�b�N���s��
            if (strMode.equals("1") || strMode.equals("3")) {

                StringBuffer strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_cost_list   ");
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

                StringBuffer strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_cost_list   ");
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

            // �yQP@40404�z TT.E Kitazawa �ۑ�Ή��F�ꗗ���o�^���̂݃`�F�b�N --- add start
            if (strMode.equals("2")) {
                // �yQP@40404�z TT.E Kitazawa �ۑ�Ή� ------------------------ add end

                // ���F���͊m�F�ς݂ł��邱��
                if (!strChkShonin.equals("")) {

                    StringBuffer strSqlBuf = new StringBuffer();

                    strSqlBuf.append("SELECT ");
                    strSqlBuf.append("   * ");
                    strSqlBuf.append("FROM ma_cost_list   ");
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

            strSql.append("UPDATE ma_cost_list SET  ");
            strSql.append("  dt_yuko    = CONVERT(DateTime, '" + strDtYuko +  "',111),  ");
                strSql.append("  biko       = '" + strBiko + "',  ");
                strSql.append("  biko_kojo       = '" + strBiko_kojo + "',  ");

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

            strSql.append("INSERT INTO ma_cost_list (  ");
            strSql.append("  cd_maker,  ");
            strSql.append("  cd_houzai,  ");
            strSql.append("  no_hansu,  ");
            strSql.append("  dt_yuko,  ");

            if(!strBiko.equals("")){
                strSql.append("  biko,  ");
            }

            // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
            if(!strBiko_kojo.equals("")){
                strSql.append("  biko_kojo,  ");
            }
            // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end

            if(!strChkKakunin.equals("")){
                strSql.append("  id_kakunin,  ");
                strSql.append("  dt_kakunin,  ");
            }

            strSql.append("  id_toroku,  ");
            strSql.append("  dt_toroku,  ");
            strSql.append("  id_koshin,  ");
            strSql.append("  dt_koshin,  ");
// �yKPX@1602367�z add start
            if(!strNameHouzai.equals("")){
                strSql.append("  name_houzai,  ");
            }
            strSql.append("  name_hansu,  ");
            strSql.append("  no_basehansu  ");
// �yKPX@1602367�z add end
            strSql.append(")  ");
            strSql.append("VALUES  ");
            strSql.append("(  ");
            strSql.append("  '" + strCdMaker  + "',  ");
            strSql.append("  '" + strCdHouzai + "',  ");
            strSql.append("  "  + strNoHansu  + ",   ");
            strSql.append("  CONVERT(DateTime, '" + strDtYuko + "',111),  ");

            if(!strBiko.equals("")){
                strSql.append("  '" + strBiko + "',   ");
            }

            // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
            if(!strBiko_kojo.equals("")){
                strSql.append("  '" + strBiko_kojo + "',   ");
            }
            // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end

            if(!strChkKakunin.equals("")){
                strSql.append("  " + userInfoData.getId_user() + ",  ");
                strSql.append("  GETDATE(),  ");
            }

            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE(),  ");
            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE()   ");
// �yKPX@1602367�z add start
            if(!strNameHouzai.equals("")){
            	strSql.append(",  '" + strNameHouzai + "',   ");
            }
            strSql.append("  '" + strNameHansu + "' ,  ");
            strSql.append("  '" + strNoBaseHansu + "'   ");
// �yKPX@1602367�z add end
            strSql.append(")  ");

            // �g�����U�N�V�����J�n
            super.createExecDB();
            execDB.BeginTran();
            execDB.execSQL(strSql.toString());

            // �폜
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_cost  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            execDB.execSQL(strSql.toString());

            for (int i = 0; i < 31; i++ ) {

                strSql = new StringBuffer();

                strSql.append("INSERT INTO ma_cost (  ");
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

                String strTitle = reqData.getFieldVale("cost", i, "nm_title");

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
                    	//strValue = String.format("%d", Integer.parseInt(strValue));
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
     * �R�X�g�l���\�[�g����
     *  : �R�X�g�l���s�Ɨ�Ń\�[�g����B
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
	    		String strValue = reqData.getFieldVale("cost", 0, strKey);
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

	        		String strValue = reqData.getFieldVale("cost", row + 1, s.getKey());

	        		if(strValue.equals("")){
	        			strValue = "@";
	        		} else {
	        			// �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
	        			strValue = String.format("%.3f", new BigDecimal(strValue));
	        			// �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end
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
}
