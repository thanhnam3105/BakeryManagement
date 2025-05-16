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
 * �yQP@30297�zNo.22
 *  �R�X�g�e�[�u���폜����
 *  : �R�X�g�e�[�u�������폜����B
 *  �@�\ID�FFGEN3110
 *
 * @author E.Kitazawa
 * @since  2014/08/21
 */
public class FGEN3110_Logic extends LogicBase{

	/**
	 * �R�X�g�e�[�u���擾����
	 * : �C���X�^���X����
	 */
	public FGEN3110_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
     * �R�X�g�e�[�u���폜
     *  : �R�X�g�e�[�u�������폜����B
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
	 */
	private void setData(
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;

        StringBuffer strSql = new StringBuffer();

        try {

			// �e�[�u����
			String strTblNm = "xmlFGEN3110";

            //----------------
			// �g�����U�N�V�����J�n
            super.createExecDB();
            execDB.BeginTran();
            //----------------
			// �@�f�[�^�擾SQL�쐬
            strSql = this.createSQL(reqData);
            //----------------
            // �R�~�b�g
            execDB.Commit();
            // �@�\ID�̐ݒ�
            String strKinoId = reqData.getID();

            resKind.setID(strKinoId);
		} catch (Exception e) {
            //----------------
            if (execDB != null) {
                // ���[���o�b�N
                execDB.Rollback();
            }
            //----------------
			this.em.ThrowException(e, "�R�X�g�e�[�u���o�^�E���F�@�f�[�^�폜���������s���܂����B");
		} finally {
            if (execDB != null) {
                // �Z�b�V�����̃N���[�Y
                execDB.Close();
                execDB = null;
            }

            //�ϐ��̍폜
            strSql = null;

			// ���X�g�̔j��
			removeList(lstRecset);

			if (searchDB != null) {
				// �Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;
			}
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
			String strCdMaker = reqData.getFieldVale(0, 0, "cd_maker");
			String strCdHouzai = reqData.getFieldVale(0, 0, "cd_houzai");
			String strNoHansu = reqData.getFieldVale(0, 0, "no_hansu");

            // �R�X�g�e�[�u���ꗗ�̑��݃`�F�b�N���s��
            StringBuffer strSqlBuf = new StringBuffer();

            strSqlBuf.append("SELECT ");
            strSqlBuf.append("   * ");
            strSqlBuf.append("FROM ma_cost_list   ");
            strSqlBuf.append("WHERE ");
            strSqlBuf.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSqlBuf.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSqlBuf.append("  no_hansu   = "  + strNoHansu  + "        ");

            super.createSearchDB();

            List<?> lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // �f�[�^�����݂��Ȃ��ꍇ
            if(lstRecset.size() == 0) {
            	// �Ώۃf�[�^�����݂��܂���B
            	em.ThrowException(ExceptionKind.���Exception,"E000301","�R�X�g�e�[�u���ꗗ","�폜","");
             }

            // ���F�ς݃f�[�^�̃`�F�b�N�H


            // �R�X�g�e�[�u���̑��݃`�F�b�N���s��
            strSqlBuf = new StringBuffer();

            strSqlBuf.append("SELECT ");
            strSqlBuf.append("   * ");
            strSqlBuf.append("FROM ma_cost   ");
            strSqlBuf.append("WHERE ");
            strSqlBuf.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSqlBuf.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSqlBuf.append("  no_hansu   = "  + strNoHansu  + "        ");

            super.createSearchDB();

            lstRecset = searchDB.dbSearch(strSqlBuf.toString());

            // �f�[�^�����݂��Ȃ��ꍇ
            if(lstRecset.size() == 0) {
            	// �Ώۃf�[�^�����݂��܂���B
            	em.ThrowException(ExceptionKind.���Exception,"E000301","�R�X�g�e�[�u��","�폜","");
             }

            // �R�X�g�f�[�^�폜
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_cost  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            // SQL���s
            execDB.execSQL(strSql.toString());

            // �R�X�g�ꗗ�폜�@�~
            strSql = new StringBuffer();
            strSql.append("DELETE FROM ma_cost_list  ");
            strSql.append("WHERE    ");
            strSql.append("  cd_maker   = '" + strCdMaker  + "'  AND   ");
            strSql.append("  cd_houzai  = '" + strCdHouzai + "'  AND   ");
            strSql.append("  no_hansu   = "  + strNoHansu  + "        ");

            // SQL���s
            execDB.execSQL(strSql.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
}
