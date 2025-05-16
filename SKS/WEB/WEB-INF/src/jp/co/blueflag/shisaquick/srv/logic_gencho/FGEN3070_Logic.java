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
 * �yQP@30297�z
 *  �R�X�g�e�[�u�����F����
 *  : �R�X�g�e�[�u�������F�o�^����B
 *  �@�\ID�FFGEN3060
 *
 * @author Sakamoto
 * @since  2014/01/25
 */
public class FGEN3070_Logic extends LogicBase{

    /**
     * �R�X�g�e�[�u�����F����
     * : �C���X�^���X����
     */
    public FGEN3070_Logic() {
        //���N���X�̃R���X�g���N�^
        super();

    }

    /**
     * �R�X�g�e�[�u�����F����
     *  : �R�X�g�e�[�u�������F�o�^����B
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
            String strTblNm = "xmlFGEN3070";

            // ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
            resKind.addTableItem(strTblNm);


        	// �@�f�[�^�擾SQL�쐬
            strSqlBuf = this.createSQL(reqData);

            // �A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
            super.createSearchDB();

            lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // �m�F�ς݂łȂ��ꍇ
			if (lstRecset.size() > 0){
				em.ThrowException(ExceptionKind.���Exception,"E000337", lstRecset.toString(), "", "");
			}

            // �C�ǉ������e�[�u���փ��R�[�h�i�[
            this.storageData(lstRecset, resKind.getTableItem(strTblNm));


        } catch (Exception e) {
            this.em.ThrowException(e, "");
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

        try {

            // ���������擾
            String strCdMaker    = toString(userInfoData.getMovement_condition().get(1));
            String strCdHouzai   = toString(userInfoData.getMovement_condition().get(2));
            String strNoHansu    = toString(userInfoData.getMovement_condition().get(3));

            strSql.append("SELECT ");
            strSql.append("   * ");
            strSql.append("FROM ma_cost_list   ");
            strSql.append("    ma_list   ");
            strSql.append("WHERE ");
            strSql.append("    id_kakunin IS NULL ");
            strSql.append("AND  ");
            strSql.append("    cd_maker = '" + strCdMaker + "'");
            strSql.append("AND  ");
            strSql.append("    cd_houzai = '" + strCdHouzai + "'");
            strSql.append("AND  ");
            strSql.append("    no_hansu = " + strNoHansu );

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
