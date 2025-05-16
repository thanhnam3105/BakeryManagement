package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * �������Z���ڌŒ�l�o�^
 *  : �c�Ƃ̊m�F�����ς݃f�[�^�ɂ��ẮA�������Z��ʂ̍��ڌŒ�l��o�^�B���f�[�^�␳�̂��߁A���o�^�f�[�^�̂ݑΏ�
 *
 * @author TT.hisahori
 * @since  2014/09/19
 *
 */

public class FGEN2021_Logic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN2021_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//              ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �������Z��� �Ǘ�����
	 * @param reqData : �@�\���N�G�X�g�f�[�^
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

			//DB�R�l�N�V����
			createSearchDB();

			StringBuffer strSQL = new StringBuffer();
			List<?> lstRecset = null;

			//���N�G�X�g�f�[�^��莎��R�[�h�擾
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

//			//�I���X�e�[�^�X�擾
//			String setting = reqData.getFieldVale("kihon", 0, "setting");
//			//���������t���O
//			String kojo = reqData.getFieldVale("kihon", 0, "busho_kojo");
//			//���݃X�e�[�^�X
//			String st_kojo = reqData.getFieldVale("kihon", 0, "st_kojo");

			//���K�\���F���l�m�F�p
			Pattern pattern = Pattern.compile("^[-]?[0-9]*[.]?[0-9]+");

			//�X�V�pSQL�쐬
			for ( int i=0; i<reqData.getCntRow("keisan"); i++ ) {
				lstRecset = null;

				String strReqSeq = reqData.getFieldVale("keisan", i, "seq_shisaku");

				//�v�Z�Œ荀�ڎ擾
				String strZyusui = toString(reqData.getFieldVale("keisan", i, "zyusui"),"",",");
				String strZyuabura = toString(reqData.getFieldVale("keisan", i, "zyuabura"),"",",");
				String strGokei = toString(reqData.getFieldVale("keisan", i, "gokei"),"",",");
				String strHiju = toString(reqData.getFieldVale("keisan", i, "hiju"),"",",");
				String strReberu = toString(reqData.getFieldVale("keisan", i, "reberu"),"",",");
				String strHijukasan = toString(reqData.getFieldVale("keisan", i, "hijukasan"),"",",");
				String strCsgenryo = toString(reqData.getFieldVale("keisan", i, "cs_genryo"),"",",");
				String strCszairyohi = toString(reqData.getFieldVale("keisan", i, "cs_zairyohi"),"",",");
				String strCsgenka = toString(reqData.getFieldVale("keisan", i, "cs_genka"),"",",");
				String strKogenka = toString(reqData.getFieldVale("keisan", i, "ko_genka"),"",",");
				String strKggenryo = toString(reqData.getFieldVale("keisan", i, "kg_genryo"),"",",");
				String strKgzairyohi = toString(reqData.getFieldVale("keisan", i, "kg_zairyohi"),"",",");
				String strKggenka = toString(reqData.getFieldVale("keisan", i, "kg_genka"),"",",");
				String strBaika = toString(reqData.getFieldVale("keisan", i, "baika"),"");
				String strArari = toString(reqData.getFieldVale("keisan", i, "arari"),"",",");

				//�e���̒P�ʁi���j�폜
				//�������̒P�ʂ͍��ڌŒ莞�̂��̂�ۑ�����ׁA�����ł̍폜�͂��Ȃ�
				Matcher matcher = pattern.matcher(strArari);
				if (matcher.find()) {
					strArari = matcher.group();
				} else {
					strArari = "";
				}

				//SELECT���쐬
				strSQL = new StringBuffer();
				strSQL.append(" SELECT cd_shain FROM");
				strSQL.append("    tr_shisan_shisaku_kotei");
				strSQL.append(" WHERE");
				strSQL.append("    cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				lstRecset = this.searchDB.dbSearch(strSQL.toString());

				if( lstRecset.isEmpty() == false ){
					break;
				}

				//���ڌŒ�`�F�b�N�Ώۂ̍��ڒl��o�^
				strSQL = null;
				strSQL = new StringBuffer();
				strSQL.append(" INSERT INTO tr_shisan_shisaku_kotei ");
				strSQL.append("            ( cd_shain ");
				strSQL.append("            , nen ");
				strSQL.append("            , no_oi ");
				strSQL.append("            , seq_shisaku ");
				strSQL.append("            , no_eda ");
				strSQL.append("            , zyusui ");
				strSQL.append("            , zyuabura ");
				strSQL.append("            , gokei ");
				strSQL.append("            , hiju ");
				strSQL.append("            , reberu ");
				strSQL.append("            , hijukasan ");
				strSQL.append("            , cs_genryo ");
				strSQL.append("            , cs_zairyohi ");
				strSQL.append("            , cs_genka ");
				strSQL.append("            , ko_genka ");
				strSQL.append("            , kg_genryo ");
				strSQL.append("            , kg_zairyohi ");
				strSQL.append("            , kg_genka ");
				strSQL.append("            , baika ");
				strSQL.append("            , arari ");
				strSQL.append("            , id_toroku ");
				strSQL.append("            , dt_toroku ");
				strSQL.append("            , id_koshin ");
				strSQL.append("            , dt_koshin )");
				strSQL.append("      VALUES");
				strSQL.append("            (" + strReqShainCd);
				strSQL.append("            ," + strReqNen);
				strSQL.append("            ," + strReqOiNo);
				strSQL.append("            ," + strReqSeq);
				strSQL.append("            ," + strReqEda);
				strSQL.append("            ,'" + strZyusui + "'");
				strSQL.append("            ,'" + strZyuabura + "'");
				strSQL.append("            ,'" + strGokei + "'");
				strSQL.append("            ,'" + strHiju + "'");
				strSQL.append("            ,'" + strReberu + "'");
				strSQL.append("            ,'" + strHijukasan + "'");
				strSQL.append("            ,'" + strCsgenryo + "'");
				strSQL.append("            ,'" + strCszairyohi + "'");
				strSQL.append("            ,'" + strCsgenka + "'");
				strSQL.append("            ,'" + strKogenka + "'");
				strSQL.append("            ,'" + strKggenryo + "'");
				strSQL.append("            ,'" + strKgzairyohi + "'");
				strSQL.append("            ,'" + strKggenka + "'");
				strSQL.append("            ,'" + strBaika + "'");
				strSQL.append("            ,'" + strArari + "'");
				strSQL.append("            ," + userInfoData.getId_user());
				strSQL.append("            ,GETDATE()");
				strSQL.append("            ," + userInfoData.getId_user());
				strSQL.append("            ,GETDATE())");

				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���ASQL�����s
				this.execDB.execSQL(strSQL.toString());

				//���ڍH���`�F�b�N���I��
				strSQL = null;
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE tr_shisan_shisaku ");
				strSQL.append(" SET fg_koumokuchk = 1 ");
				strSQL.append(" WHERE ");
				strSQL.append("     cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				this.execDB.execSQL(strSQL.toString());

			}
			// �R�~�b�g
			execDB.Commit();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			resKind.getTableItem(strTableNm);

			//�������ʂ̊i�[
			resKind.getTableItem(strTableNm).addFieldVale(0, "flg_return", "true");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_error", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "no_errmsg", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "nm_class", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "cd_error", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_system", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_cd", "0");

		} catch (Exception e) {
			// ���[���o�b�N
			execDB.Rollback();
			this.em.ThrowException(e, "�������Z��� �Ǘ����쏈�������s���܂����B");

		} finally {
			//�Z�b�V�����̃N���[�Y
			if (execDB != null) {
				execDB.Close();
				execDB = null;
			}
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
		}
		return resKind;
	}
}
