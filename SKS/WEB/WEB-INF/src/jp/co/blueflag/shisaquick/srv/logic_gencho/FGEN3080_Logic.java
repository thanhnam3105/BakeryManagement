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
 *  �R�X�g�e�[�u�����݃`�F�b�N
 *  : �R�X�g�R�X�g�e�[�u�����݃`�F�b�N������B
 *  �@�\ID�FFGEN3080
 *
 * @author Sakamoto
 * @since  2014/01/25
 */
public class FGEN3080_Logic extends LogicBase{

    /**
     *  �R�X�g�e�[�u�����݃`�F�b�N
     * : �C���X�^���X����
     */
    public FGEN3080_Logic() {
        //���N���X�̃R���X�g���N�^
        super();

    }

    /**
     *  �R�X�g�e�[�u�����݃`�F�b�N
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

        // ���R�[�h�l�i�[���X�g
        List<?> lstRecset = null;

        // ���R�[�h�l�i�[���X�g
        StringBuffer strSqlBuf = null;

        try {

            // �e�[�u����
            String strTblNm = "xmlFGEN3080";

            // �@�f�[�^�擾SQL�쐬
            strSqlBuf = this.createSQL(reqData);

            // �A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
            super.createSearchDB();
            lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // �������ʂ�����ꍇ
            if (lstRecset.size() > 0){
				em.ThrowException(ExceptionKind.���Exception,"E000336", "", "", "");
            }

            // �B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
            resKind.addTableItem(strTblNm);

            // �C�ǉ������e�[�u���փ��R�[�h�i�[
            this.storageData(lstRecset, resKind.getTableItem(strTblNm));

        } catch (Exception e) {
            this.em.ThrowException(e, "");

        } finally {

            // ���X�g�̔j��
            removeList(lstRecset);

            if (searchDB != null) {
                // �Z�b�V�����̉��
                this.searchDB.Close();
                searchDB = null;
            }

            // �ϐ��̍폜
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

            // ���������擾
            String strCdMaker = reqData.getFieldVale(0, 0, "cd_maker");
            String strCdHouzai = reqData.getFieldVale(0, 0, "cd_houzai");
            String strNoHansu = reqData.getFieldVale(0, 0, "no_hansu");

            // ���[�J�[��
            if(!strCdMaker.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" LST.cd_maker = " + strCdMaker);
            }

            // ���
            if(!strCdHouzai.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" LST.cd_houzai = " + strCdHouzai);
            }

            // �Ő�
            if(!strNoHansu.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" LST.no_hansu = " + strNoHansu);
            }

            // SQL���̍쐬
            strSql.append("SELECT ");
            strSql.append("    LST.cd_maker AS cd_maker, ");
            strSql.append("    LST.cd_houzai AS cd_houzai, ");
            strSql.append("    LST.no_hansu AS no_hansu ");
            strSql.append("FROM ");
            strSql.append("    ma_cost_list LST  ");

            if(strWork.length() > 0){
                strWhere.append(" WHERE ");
                strWhere.append(strWork);
            }

            strSql.append(strWhere);

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
            }

        } catch (Exception e) {
            this.em.ThrowException(e, "");

        } finally {

        }
    }
}
