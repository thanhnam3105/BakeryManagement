package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *  �R�X�g�Ő�MAX�l�擾
 *  : �R�X�g�Ő�MAX�l���擾����B
 *  �@�\ID�FFGEN3660
 *
 * @author BRC Koizumi
 * @since  2016/10/21
 */
public class FGEN3660_Logic extends LogicBase{

    private String strCdMaker = "";
    private String strCdHouzai = "";

	/**
     * �R�X�g�Ő�MAX�l�擾����
     * : �C���X�^���X����
     */
    public FGEN3660_Logic() {
        //���N���X�̃R���X�g���N�^
        super();

    }

    /**
     * �R�X�g�Ő�MAX�l�擾
     *  : �R�X�g�Ő�MAX�l���擾����B
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
     * @author BRC Koizumi
     * @since  2016/09/07
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
            String strTblNm = "xmlFGEN3660";

            // ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
            resKind.addTableItem(strTblNm);

            	this.strCdMaker = toString(userInfoData.getMovement_condition().get(1));		// ���[�J�[�R�[�h
            	this.strCdHouzai = toString(userInfoData.getMovement_condition().get(2));		// �ł̕�ރR�[�h

            	// �@�f�[�^�擾SQL�쐬
                strSqlBuf = this.createSQL(reqData);

                // �A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
                super.createSearchDB();
                lstRecset = searchDB.dbSearch(strSqlBuf.toString());

                // �������ʂ��Ȃ��ꍇ
    			if (lstRecset.size() == 0){
    				this.emptyData(resKind.getTableItem(strTblNm));
    			}

                // �C�ǉ������e�[�u���փ��R�[�h�i�[
                this.storageData(lstRecset, resKind.getTableItem(strTblNm));


        } catch (Exception e) {
            this.em.ThrowException(e, "�x�[�X�P���@�f�[�^�������������s���܂����B");

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

            // ���[�J�[�R�[�h
            if(!this.strCdMaker.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" cd_maker = " + this.strCdMaker);
            }

            // �x�[�X��ރR�[�h
            if(!this.strCdHouzai.equals("")){
                if(strWork.length() > 0){
                    strWork.append(" AND ");
                }
                strWork.append(" cd_houzai = " + this.strCdHouzai);
            }

            // �L���J�n��
//            if(strWork.length() > 0){
//				strWork.append(" AND ");
//			}
//			strWork.append(" dt_yuko < GETDATE()  ");

            // SQL���̍쐬
            strSql.append("SELECT ");
            strSql.append("    MAX(no_hansu) as no_hansu ");	// �Ő��i�ő�l�j
            strSql.append("FROM ");
            strSql.append("    ma_cost_list ");

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
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
    private void emptyData(
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

            // ���ʂ����X�|���X�f�[�^�Ɋi�[
            resTable.addFieldVale(0, "no_hansu",  "1");					// �Ő�

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
        	// �������ʂ̊i�[
            resTable.addFieldVale(0, "flg_return", "true");
            resTable.addFieldVale(0, "msg_error", "");
            resTable.addFieldVale(0, "no_errmsg", "");
            resTable.addFieldVale(0, "nm_class", "");
            resTable.addFieldVale(0, "cd_error", "");
            resTable.addFieldVale(0, "msg_system", "");

            Object items = (Object) lstGenkaHeader.get(0);

            // ���ʂ����X�|���X�f�[�^�Ɋi�[
            resTable.addFieldVale(0, "no_hansu", toString(items,""));		// �Ő�

        } catch (Exception e) {
            this.em.ThrowException(e, "");

        } finally {

        }

    }

}
