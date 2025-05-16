package jp.co.blueflag.shisaquick.srv.logic_gencho;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * �yQP@30297�z
 *  �R�X�g�e�[�u���擾
 *  : �R�X�g�e�[�u�������擾����B
 *  �@�\ID�FFGEN3030
 *
 * @author Sakamoto
 * @since  2014/01/25
 */
public class FGEN3030_Logic extends LogicBase{

    private String strCdMaker = "";
    private String strCdHouzai = "";
    private String strNoHansu = "";
    private String strChkMishiyo = ""; //�yKPX@1602367�zadd

	/**
     * �R�X�g�e�[�u���擾����
     * : �C���X�^���X����
     */
    public FGEN3030_Logic() {
        //���N���X�̃R���X�g���N�^
        super();

    }

    /**
     * �R�X�g�e�[�u���擾
     *  : �R�X�g�e�[�u�������擾����B
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
            this.setData(resKind, reqData);

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
    private void setData(
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
            String strTblNm = "xmlFGEN3030";

            // ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
            resKind.addTableItem(strTblNm);

            // �������[�h���擾����
            String strMode = toString(userInfoData.getMovement_condition().get(0));

            String strBack = toString(userInfoData.getMovement_condition().get(4));

            // �V�K
            if (strMode.equals("1")) {

            	// �ǉ������e�[�u���փ��R�[�h�i�[
                this.emptyData(resKind.getTableItem(strTblNm));


            }
            // �o�^�E���F
            else {

            	this.strCdMaker = toString(userInfoData.getMovement_condition().get(1));
            	this.strCdHouzai = toString(userInfoData.getMovement_condition().get(2));
            	this.strNoHansu = toString(userInfoData.getMovement_condition().get(3));
            	this.strChkMishiyo = toString(userInfoData.getMovement_condition().get(5));	// ���g�p�`�F�b�N

            	// �@�f�[�^�擾SQL�쐬
                strSqlBuf = this.createSQL(reqData);

                // �A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
                super.createSearchDB();
                lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                // �������ʂ��Ȃ��ꍇ
    			if (lstRecset.size() == 0){
    				em.ThrowException(ExceptionKind.�x��Exception,"W000401", lstRecset.toString(), "", "");
    			}

                // �C�ǉ������e�[�u���փ��R�[�h�i�[
                this.storageData(lstRecset, resKind.getTableItem(strTblNm));

            }

            resKind.getTableItem(strTblNm).addFieldVale(0, "mode", strMode);
            resKind.getTableItem(strTblNm).addFieldVale(0, "back", strBack);
            resKind.getTableItem(strTblNm).addFieldVale(0, "chk_mishiyo", this.strChkMishiyo);

        } catch (Exception e) {
            this.em.ThrowException(e, "�R�X�g�e�[�u���Q�Ɓ@�f�[�^�������������s���܂����B");

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

        // SQL�i�[�p
        StringBuffer strSql = new StringBuffer();
        StringBuffer strWork = new StringBuffer();
        StringBuffer strWhere = new StringBuffer();

        try {

            // ���[�J�[��
            if(!this.strCdMaker.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" M1.cd_maker = " + this.strCdMaker);
            }

            // ���
            if(!this.strCdHouzai.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" M1.cd_houzai = " + this.strCdHouzai);
            }

            // �Ő�
            if(!this.strNoHansu.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" M1.no_hansu = " + this.strNoHansu);
            }

            // ���g�p
            if(strChkMishiyo.equals("1")){
            	if(strWork.length() > 0){
            		strWork.append(" AND ");
            	}
            	strWork.append(" ISNULL(M4.flg_mishiyo,'0') = " + this.strChkMishiyo);
            } else{
				if(strWork.length() > 0){
					strWork.append(" AND ");
				}
				strWork.append(" ISNULL(M4.flg_mishiyo,'0') != '1' ");
			}

            // SQL���̍쐬
            strSql.append("SELECT ");
            strSql.append("    M1.cd_maker, ");
            strSql.append("    M1.cd_houzai, ");
            strSql.append("    M1.no_hansu, ");
			strSql.append("    CONVERT(VARCHAR,LST.dt_yuko,111) AS dt_yuko,");
			strSql.append("    LST.biko,");
//			strSql.append("    LST.id_toroku,");
			strSql.append("    LST.id_kakunin,");
			strSql.append("    M2.nm_user AS nm_kakunin,");
			strSql.append("    LST.id_shonin,");
			strSql.append("    M3.nm_user AS nm_shonin,");
            strSql.append("    M1.no_row, ");
            strSql.append("    M1.nm_title, ");
            strSql.append("    M1.no_value01, ");
            strSql.append("    M1.no_value02, ");
            strSql.append("    M1.no_value03, ");
            strSql.append("    M1.no_value04, ");
            strSql.append("    M1.no_value05, ");
            strSql.append("    M1.no_value06, ");
            strSql.append("    M1.no_value07, ");
            strSql.append("    M1.no_value08, ");
            strSql.append("    M1.no_value09, ");
            strSql.append("    M1.no_value10, ");
            strSql.append("    M1.no_value11, ");
            strSql.append("    M1.no_value12, ");
            strSql.append("    M1.no_value13, ");
            strSql.append("    M1.no_value14, ");
            strSql.append("    M1.no_value15, ");
            strSql.append("    M1.no_value16, ");
            strSql.append("    M1.no_value17, ");
            strSql.append("    M1.no_value18, ");
            strSql.append("    M1.no_value19, ");
            strSql.append("    M1.no_value20, ");
            strSql.append("    M1.no_value21, ");
            strSql.append("    M1.no_value22, ");
            strSql.append("    M1.no_value23, ");
            strSql.append("    M1.no_value24, ");
            strSql.append("    M1.no_value25, ");
            strSql.append("    M1.no_value26, ");
            strSql.append("    M1.no_value27, ");
            strSql.append("    M1.no_value28, ");
            strSql.append("    M1.no_value29, ");
            strSql.append("    M1.no_value30  ");
            strSql.append("    ,LST.biko_kojo  ");

//�yKPX@1602367�zadd start
//            strSql.append("    ,LST.name_houzai  ");								// �x�[�X��ޖ�
            strSql.append("    ,LST.name_hansu  ");								// �ł̕�ޖ�
            strSql.append("    ,LST.no_basehansu  ");							// �x�[�X�Ő�
            strSql.append("    ,ISNULL(M4.flg_mishiyo,'0') AS  flg_mishiyo ");								// ���g�p�t���O
//�yKPX@1602367�zadd end

            strSql.append("FROM ");
            strSql.append("    ma_cost M1");
			strSql.append("    LEFT JOIN ma_cost_list LST ON  ");
			strSql.append("    M1.cd_maker  = LST.cd_maker AND ");
			strSql.append("    M1.cd_houzai = LST.cd_houzai AND ");
			strSql.append("    M1.no_hansu  = LST.no_hansu ");
//			strSql.append("    LEFT JOIN ma_user M2 ON M2.id_user = LST.id_toroku");
			strSql.append("    LEFT JOIN ma_user M2 ON M2.id_user = LST.id_kakunin");
			strSql.append("    LEFT JOIN ma_user M3 ON M3.id_user = LST.id_shonin");
			strSql.append("    LEFT JOIN ma_literal M4 ON M4.cd_category = 'maker_name' ");
			strSql.append("      AND M4.cd_literal = LST.cd_maker");
			strSql.append("      AND M4.cd_2nd_literal = LST.cd_houzai");

            if(strWork.length() > 0){
                strWhere.append(" WHERE ");
                strWhere.append(strWork);
            }

            strSql.append(strWhere);
            strSql.append("  ORDER BY no_row ASC");

        } catch (Exception e) {
            this.em.ThrowException(e, "");
        } finally {

        }
        return strSql;
    }

    /**
     * �p�����[�^�[�i�[
     *  : �X�e�[�^�X������ʂւ̃��X�|���X�f�[�^�֊i�[����B
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void emptyData(
             RequestResponsTableBean resTable
            )
    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

        try {

            for (int i = 0; i < 31; i++) {

                // �������ʂ̊i�[
                resTable.addFieldVale(i, "flg_return", "true");
                resTable.addFieldVale(i, "msg_error", "");
                resTable.addFieldVale(i, "no_errmsg", "");
                resTable.addFieldVale(i, "nm_class", "");
                resTable.addFieldVale(i, "cd_error", "");
                resTable.addFieldVale(i, "msg_system", "");

                // ���ʂ����X�|���X�f�[�^�Ɋi�[
                resTable.addFieldVale(i, "cd_maker", "");
                resTable.addFieldVale(i, "cd_houzai", "");
                resTable.addFieldVale(i, "no_hansu",  "");
                resTable.addFieldVale(i, "dt_yuko",  "");
                resTable.addFieldVale(i, "biko",  "");

                // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
                resTable.addFieldVale(i, "biko_kojo",  "");
                // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end

                resTable.addFieldVale(i, "flg_mishiyo",  "");		//���g�p�t���O�yKPX@1602367�zmod

                resTable.addFieldVale(i, "id_kakunin",  "");
                resTable.addFieldVale(i, "id_shonin",  "");
                resTable.addFieldVale(i, "no_row", "");
                resTable.addFieldVale(i, "nm_title", "");
                resTable.addFieldVale(i, "no_row",Integer.toString(i));
                resTable.addFieldVale(i, "nm_title", "");

                for (int j = 0; j < 30; j++) {
                    resTable.addFieldVale(i, String.format("no_value%02d", j + 1), "");
                }
            }

        } catch (Exception e) {
            this.em.ThrowException(e, "");

        } finally {

        }

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
              List<?> lstGenkaHeader
            , RequestResponsTableBean resTable
            )
    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

        try {

        	for (int i = 0; i < lstGenkaHeader.size(); i++) {

                // �������ʂ̊i�[
                resTable.addFieldVale(i, "flg_return", "true");
                resTable.addFieldVale(i, "msg_error", "");
                resTable.addFieldVale(i, "no_errmsg", "");
                resTable.addFieldVale(i, "nm_class", "");
                resTable.addFieldVale(i, "cd_error", "");
                resTable.addFieldVale(i, "msg_system", "");

                Object[] items = (Object[]) lstGenkaHeader.get(i);

                // ���ʂ����X�|���X�f�[�^�Ɋi�[
                resTable.addFieldVale(i, "cd_maker", toString(items[0],""));
                resTable.addFieldVale(i, "cd_houzai", toString(items[1],""));
                resTable.addFieldVale(i, "no_hansu", toString(items[2],""));
                resTable.addFieldVale(i, "dt_yuko", toString(items[3],""));
                resTable.addFieldVale(i, "biko", toString(items[4],""));

            	// �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod start
                resTable.addFieldVale(i, "biko_kojo", toString(items[41],""));
                // �yQP@30297�z TT.nishigawa �ۑ�Ή� --------------------- mod end

//                resTable.addFieldVale(i, "name_houzai", toString(items[42],""));	// �x�[�X��ޖ��yKPX@1602367�zmod
                resTable.addFieldVale(i, "name_hansu", toString(items[42],""));		//�R�X�g��ޖ��yKPX@1602367�zmod
                resTable.addFieldVale(i, "no_basehansu", toString(items[43],""));	//�x�[�X�Ő��yKPX@1602367�zmod
                resTable.addFieldVale(i, "flg_mishiyo", toString(items[44],""));	//���g�p�t���O�yKPX@1602367�zmod

                resTable.addFieldVale(i, "id_kakunin", toString(items[5],""));
                resTable.addFieldVale(i, "nm_kakunin", toString(items[6],""));
                resTable.addFieldVale(i, "id_shonin", toString(items[7],""));
                resTable.addFieldVale(i, "nm_shonin", toString(items[8],""));
                resTable.addFieldVale(i, "no_row", toString(items[9],""));
                resTable.addFieldVale(i, "nm_title", toString(items[10],""));

                for (int j = 0; j < 30; j++) {
                    resTable.addFieldVale(i, String.format("no_value%02d", j + 1), toString(items[j + 11], ""));
                }
            }

        } catch (Exception e) {
            this.em.ThrowException(e, "");

        } finally {

        }

    }

}
