package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yQP@40404�z
 *  �Q�l�����Ǘ��c�a�o�^
 *  �@�\ID�FFGEN3220
 *
 * @author E.Kitazawa
 * @since  2014/09/04
 */
public class FGEN3220_Logic extends LogicBase{

	/**
     * �Q�l�����Ǘ��c�a�o�^����
     * : �C���X�^���X����
     */
    public FGEN3220_Logic() {
        //���N���X�̃R���X�g���N�^
        super();

    }

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

    /**
     * �Q�l�����Ǘ��c�a�o�^
     *  : �Q�l��������o�^����B
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

            // �g�����U�N�V�����J�n
            super.createExecDB();

            execDB.BeginTran();

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
            this.storageData(resKind.getTableItem(strTableNm));

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
     * �f�[�^�X�VSQL�쐬
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

            String strCdLiteral   = reqData.getFieldVale("table", 0, "cd_literal");
            String strNmLiteral   = reqData.getFieldVale("table", 0, "nm_literal");
            String strFileNm      = reqData.getFieldVale("table", 0, "nm_file");

            strSql.append("UPDATE tr_sankoshiryo SET  ");
            strSql.append("  nm_literal  = '" + strNmLiteral + "',  ");
            strSql.append("  nm_file  = '" + strFileNm + "',  ");
            strSql.append("  id_koshin  = " + userInfoData.getId_user() + ",  ");
            strSql.append("  dt_koshin  = GETDATE()  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_literal  = '" + strCdLiteral + "' ");

            strSql.append("IF @@ROWCOUNT = 0    ");

            strSql.append("INSERT INTO tr_sankoshiryo (  ");
            strSql.append("  cd_literal,  ");
            strSql.append("  nm_literal,  ");
            strSql.append("  nm_file,  ");

            strSql.append("  id_toroku,  ");
            strSql.append("  dt_toroku,  ");
            strSql.append("  id_koshin,  ");
            strSql.append("  dt_koshin  ");
            strSql.append(")  ");
            strSql.append("VALUES  ");
            strSql.append("(  ");
            strSql.append("  '" + strCdLiteral + "',  ");
            strSql.append("  '"  + strNmLiteral  + "',   ");
            strSql.append("  '"  + strFileNm  + "',   ");

            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE(),  ");
            strSql.append("  " + userInfoData.getId_user() + ",  ");
            strSql.append("  GETDATE()   ");
            strSql.append(")  ");

            execDB.execSQL(strSql.toString());


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

}
