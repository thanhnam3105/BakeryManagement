package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 *  �x�[�X�P�����F����
 *  : �x�[�X�P�������F�o�^����B
 *  �@�\ID�FFGEN3540
 *
 * @author BRC Koizumi
 * @since  2016/09/07
 */
public class FGEN3540_Logic extends LogicBase{

    /**
     * �x�[�X�P�����F����
     * : �C���X�^���X����
     */
    public FGEN3540_Logic() {
        //���N���X�̃R���X�g���N�^
        super();

    }

    /**
     * �x�[�X�P�����F����
     *  : �x�[�X�P�������F�o�^����B
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

        //���X�|���X�f�[�^�N���X
        RequestResponsKindBean resKind = null;

        try {

            //���X�|���X�f�[�^�i�@�\�j����
            resKind = new RequestResponsKindBean();

            // �g�����U�N�V�����J�n
            super.createExecDB();

            execDB.BeginTran();

            update(reqData);

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

        }
        return resKind;
    }

    /**
     * ���F�X�V����
     * @param reqData�F���N�G�X�g�f�[�^
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void update(
            RequestResponsKindBean reqData
            )
    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

        //SQL�i�[�p
        StringBuffer strSql = new StringBuffer();

        try {

            String strCdMaker    = reqData.getFieldVale("base_price_list", 0, "cd_maker");
            String strCdHouzai   = reqData.getFieldVale("base_price_list", 0, "cd_houzai");
            String strNoHansu    = reqData.getFieldVale("base_price_list", 0, "no_hansu");
            String strChkShonin  = reqData.getFieldVale("base_price_list", 0, "chk_shonin");

            // ���F���͊m�F�ς݂ł��邱��
            if (!strChkShonin.equals("")) {

                StringBuffer strSqlBuf = new StringBuffer();

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

            // �m�F�҂Ə��F�҂͈قȂ邱��
            if (!strChkShonin.equals("")) {

                StringBuffer strSqlBuf = new StringBuffer();

                strSqlBuf.append("SELECT ");
                strSqlBuf.append("   * ");
                strSqlBuf.append("FROM ma_base_price_list   ");
                strSqlBuf.append("    ma_list   ");
                strSqlBuf.append("WHERE ");
                strSqlBuf.append("    id_kakunin = " + userInfoData.getId_user() + "  ");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_maker = '" + strCdMaker + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    cd_houzai = '" + strCdHouzai + "'");
                strSqlBuf.append("AND  ");
                strSqlBuf.append("    no_hansu = " + strNoHansu );

                super.createSearchDB();

                List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                if(lstRecset.size() > 0) {
                    em.ThrowException(ExceptionKind.���Exception,"E000339" ,"","","");
                }
            }

            strSql.append("UPDATE ma_base_price_list SET  ");

            if(!strChkShonin.equals("")){
                strSql.append("  id_shonin  = " + userInfoData.getId_user() + ",  ");
                strSql.append("  dt_shonin  = GETDATE(),  ");
            } else {
                strSql.append("  id_shonin  = NULL,  ");
                strSql.append("  dt_shonin  = NULL,  ");
            }

            strSql.append("  id_koshin  = " + userInfoData.getId_user() + ",  ");
            strSql.append("  dt_koshin  = GETDATE()  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            execDB.execSQL(strSql.toString());

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
}
