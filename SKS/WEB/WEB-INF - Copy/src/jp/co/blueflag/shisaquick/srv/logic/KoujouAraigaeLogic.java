package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;


/**
 * �������A���/�H��i�����j�􂢊���
 * :�􂢊�����H��Ō��������Ď擾���A�Ď擾��̌����������X�|���X�ɐݒ肷��B
 * @author isono
 * @since  2009/06/07
 */
public class KoujouAraigaeLogic extends LogicBase {

	/**
	 * �������A���/�H��i�����j�􂢊����R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public KoujouAraigaeLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���͌���DB�����������W�b�N�Ǘ�
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
		//�����pSQL�i�[�p�o�b�t�@
		StringBuffer strSql = new StringBuffer();
		//�������ʊi�[�p���X�g
		List<?> lstRecset = null;
		//2011/06/02 QP@10181_No.66 TT T.Satoh Add Start -------------------------
		//�������ʊi�[�p���X�g(�������p)
		List<?> kenkyujoRecset = null;
		//2011/06/02 QP@10181_No.66 TT T.Satoh Add End ---------------------------

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {

			//�f�[�^�̌���

			//SQL����
			//2011/06/02 QP@10181_No.66 TT T.Satoh Change Start -------------------------
			//�������������̏ꍇ
			if(reqData.getFieldVale(0, 0, "cd_new_busho").equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KOJO"))
					&& reqData.getFieldVale(0, 0, "cd_new_kaisha").equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KAISHA"))){
				strSql = this.AraigaeSQL(reqData);
				//DB�����p�Z�b�V�������擾
				this.createSearchDB();
				//�������s���ʎ擾
				lstRecset = this.searchDB.dbSearch(strSql.toString());

				//�����pSQL�i�[�p�o�b�t�@������
				strSql.delete(0, strSql.length());
				//�������̌����f�[�^�擾�pSQL�쐬
				strSql = this.KenkyujoSQL(reqData);
				//DB�����p�Z�b�V�������擾
				this.createSearchDB();
				//�������s���ʎ擾
				kenkyujoRecset = this.searchDB.dbSearch(strSql.toString());
			}
			//�������������ȊO�̏ꍇ
			else {
				strSql = this.CreateSQL(reqData);
				//DB�����p�Z�b�V�������擾
				this.createSearchDB();
				//�������s���ʎ擾
				lstRecset = this.searchDB.dbSearch(strSql.toString());
			}

//			strSql = this.CreateSQL(reqData);
//			//DB�����p�Z�b�V�������擾
//			this.createSearchDB();
//			//�������s���ʎ擾
//			lstRecset = this.searchDB.dbSearch(strSql.toString());
			//2011/06/02 QP@10181_No.66 TT T.Satoh Change End ---------------------------

// 20160513  KPX@1600766 ADD start
			//���쌴���f�[�^�̉�Ђ̒P���J���������擾����i���e�����}�X�^�j
			boolean blnKengen = getTankaHyouji_kengen(reqData, userInfoData);
// 20160513  KPX@1600766 ADD end
			//���X�|���X�̐���

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			// �@�\ID�̐ݒ�
			resKind.setID(toString(reqData.getID()));
			// �e�[�u�����̐ݒ�
			resKind.addTableItem(toString(reqData.getTableID(0)));
			//�������ʂ����X�|���X�f�[�^�Ɋi�[
			//2011/06/06 QP@10181_No.66 TT T.Satoh Change Start -------------------------
			//storageData(lstRecset, resKind.getTableItem(0), reqData);

// 20160513  KPX@1600766 MOD start
			//�P���J���s�̉�Ђ̏ꍇ�A�擾�������쌴���f�[�^���P�����󔒂ɕύX���ĕԂ��B
//			storageData(lstRecset, kenkyujoRecset, resKind.getTableItem(0), reqData);
			storageData(lstRecset, kenkyujoRecset, resKind.getTableItem(0), reqData, blnKengen);
// 20160513  KPX@1600766 MOD start
			//2011/06/06 QP@10181_No.66 TT T.Satoh Change End ---------------------------

		} catch (Exception e) {
			this.em.ThrowException(e, "���͌�������DB�����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			lstRecset = null;

			//�Z�b�V�����̃N���[�Y
			searchDB.Close();
			searchDB = null;

			//�ϐ��̍폜
			strSql = null;

		}
		return resKind;

	}

	/**
	 * ���͌����f�[�^�擾SQL�쐬
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @return ���͌��������pSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public StringBuffer CreateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//�r�p�k
		StringBuffer strRetSQL = new StringBuffer();
		//��������
		String strGenryoVar = "";

		try {

			//���������𐶐�
			strGenryoVar = MakeGenryoVar(reqData);

			//SQL�̍쐬
			strRetSQL.append("SELECT ");
			strRetSQL.append("  M401.cd_genryo     AS ����CD ");
			strRetSQL.append(" ,M401.cd_kaisha     AS ���CD ");
			strRetSQL.append(" ,M402_1.cd_busho    AS ����CD ");
			strRetSQL.append(" ,M402_1.nm_genryo   AS ��������1  ");
			strRetSQL.append(" ,M402_2.nm_genryo   AS ��������2 ");
			strRetSQL.append(" ,M402_1.tanka       AS �P��  ");
			strRetSQL.append(" ,M402_1.budomari    AS ����  ");
			strRetSQL.append(" ,M401.ritu_abura    AS ���ܗL��  ");
			strRetSQL.append(" ,M401.ritu_sakusan  AS �|�_  ");
			strRetSQL.append(" ,M401.ritu_shokuen  AS �H��  ");
			strRetSQL.append(" ,M401.ritu_sousan   AS ���_  ");
			strRetSQL.append(" ,M402_1.budomari    AS �}�X�^����  ");
			strRetSQL.append("FROM ");
			strRetSQL.append(" ma_genryo AS M401 ");
			strRetSQL.append(" LEFT JOIN ma_genryokojo AS M402_1  ");
			strRetSQL.append(" ON  M401.cd_kaisha = M402_1.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_1.cd_genryo ");
			strRetSQL.append(" AND (M402_1.cd_busho = " + reqData.getFieldVale(0, 0, "cd_new_busho") + " OR M402_1.cd_busho = 0)");
			strRetSQL.append(" LEFT JOIN ( ");
			strRetSQL.append("    SELECT DISTINCT ");
			strRetSQL.append("      cd_kaisha ");
			strRetSQL.append("    , cd_genryo ");
			strRetSQL.append("    , nm_genryo ");
			strRetSQL.append("    FROM ");
			strRetSQL.append("       ma_genryokojo ");
			strRetSQL.append("    WHERE ");
			strRetSQL.append("       cd_genryo IN (" + strGenryoVar + ") ");
			strRetSQL.append("    ) AS M402_2  ");
			strRetSQL.append(" ON  M401.cd_kaisha = M402_2.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_2.cd_genryo ");
			strRetSQL.append("WHERE ");
			strRetSQL.append(" M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("AND M401.cd_genryo IN (" + strGenryoVar + ") ");

		} catch (Exception e) {
			this.em.ThrowException(e, "���/�H��􂢊����c�a���������Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜

		}
		//�A:�쐬����SQL��ԋp����B
		return strRetSQL;

	}

	//2011/05/30 QP@10181_No.66 TT T.Satoh Add Start -------------------------
	/**
	 * �������p���͌����f�[�^�擾SQL�쐬
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @return ���͌��������pSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public StringBuffer AraigaeSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//�r�p�k
		StringBuffer strRetSQL = new StringBuffer();
		//��������
		String strGenryoVar = "";

		try {

			//���������𐶐�
			strGenryoVar = MakeGenryoVar(reqData);

			//SQL�̍쐬
			strRetSQL.append("SELECT ");
			strRetSQL.append("  DISTINCT M401.cd_genryo   AS ����CD ");
			strRetSQL.append(" ,M401.cd_kaisha            AS ���CD ");
			strRetSQL.append(" ,M402_1.cd_busho           AS ����CD ");
			strRetSQL.append(" ,('��' + M402_1.nm_genryo) AS ��������1 ");
			strRetSQL.append(" ,('��' + M402_1.nm_genryo) AS ��������2 ");
			strRetSQL.append(" ,M402_2.tanka              AS �P��  ");
			strRetSQL.append(" ,M402_3.budomari           AS ����  ");
			strRetSQL.append(" ,M401.ritu_abura           AS ���ܗL��  ");
			strRetSQL.append(" ,M401.ritu_sakusan         AS �|�_  ");
			strRetSQL.append(" ,M401.ritu_shokuen         AS �H��  ");
			strRetSQL.append(" ,M401.ritu_sousan          AS ���_  ");
			strRetSQL.append(" ,M402_3.budomari           AS �}�X�^����  ");
			strRetSQL.append("FROM ");
			strRetSQL.append(" ma_genryo AS M401 ");
			strRetSQL.append(" LEFT JOIN ma_genryokojo AS M402_1 ");
			strRetSQL.append(" ON  M401.cd_kaisha = M402_1.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_1.cd_genryo ");
			strRetSQL.append(" INNER JOIN ( ");
			strRetSQL.append("    SELECT ");
			strRetSQL.append("      M401.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("    , M401.cd_genryo AS cd_genryo ");
			strRetSQL.append("    , MAX(M402.tanka) AS tanka ");
			strRetSQL.append("    FROM ma_genryo M401");
			strRetSQL.append("    LEFT JOIN ma_genryokojo M402 ");
			strRetSQL.append("    ON M401.cd_kaisha = M402.cd_kaisha ");
			strRetSQL.append("    AND M401.cd_genryo = M402.cd_genryo ");
			strRetSQL.append("    WHERE M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("       AND M401.cd_genryo IN (" + strGenryoVar + ") ");
			strRetSQL.append("    GROUP BY M401.cd_kaisha ");
			strRetSQL.append("    , M401.cd_genryo) AS M402_2 ");
			strRetSQL.append(" ON M401.cd_kaisha = M402_2.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_2.cd_genryo ");
			strRetSQL.append(" AND M402_1.tanka = M402_2.tanka ");
			strRetSQL.append(" LEFT JOIN ( ");
			strRetSQL.append("    SELECT ");
			strRetSQL.append("      M401.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("    , M401.cd_genryo AS cd_genryo ");
			strRetSQL.append("    , MIN(M402.budomari) AS budomari ");
			strRetSQL.append("    FROM ma_genryo M401 ");
			strRetSQL.append("    LEFT JOIN ma_genryokojo M402 ");
			strRetSQL.append("    ON M401.cd_kaisha = M402.cd_kaisha ");
			strRetSQL.append("    AND M401.cd_genryo = M402.cd_genryo ");
			strRetSQL.append("    WHERE M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("       AND M401.cd_genryo IN (" + strGenryoVar + ") ");
			strRetSQL.append("    GROUP BY M401.cd_kaisha ");
			strRetSQL.append("    , M401.cd_genryo) AS M402_3 ");
			strRetSQL.append(" ON M401.cd_kaisha = M402_3.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_3.cd_genryo ");
			strRetSQL.append("WHERE ");
			strRetSQL.append(" M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("AND M401.cd_genryo IN (" + strGenryoVar + ") ");

		} catch (Exception e) {
			this.em.ThrowException(e, "���/�H��􂢊����c�a���������Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜

		}
		//�A:�쐬����SQL��ԋp����B
		return strRetSQL;

	}

	/**
	 * �������p���͌����f�[�^�擾SQL�쐬
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @return ���͌��������pSQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public StringBuffer KenkyujoSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//�r�p�k
		StringBuffer strRetSQL = new StringBuffer();
		//��������
		String strGenryoVar = "";

		try {

			//���������𐶐�
			strGenryoVar = MakeGenryoVar(reqData);

			//SQL�̍쐬
			strRetSQL.append("SELECT ");
			strRetSQL.append("  DISTINCT M401.cd_genryo AS ����CD ");
			strRetSQL.append(" ,M401.cd_kaisha          AS ���CD ");
			strRetSQL.append(" ,M402_1.cd_busho         AS ����CD ");
			strRetSQL.append(" ,M402_1.nm_genryo        AS ��������1 ");
			strRetSQL.append(" ,M402_1.nm_genryo        AS ��������2 ");
			strRetSQL.append(" ,M402_1.tanka            AS �P��  ");
			strRetSQL.append(" ,M402_1.budomari         AS ����  ");
			strRetSQL.append(" ,M401.ritu_abura         AS ���ܗL��  ");
			strRetSQL.append(" ,M401.ritu_sakusan       AS �|�_  ");
			strRetSQL.append(" ,M401.ritu_shokuen       AS �H��  ");
			strRetSQL.append(" ,M401.ritu_sousan        AS ���_  ");
			strRetSQL.append(" ,M402_1.budomari         AS �}�X�^����  ");
			strRetSQL.append("FROM ");
			strRetSQL.append(" ma_genryo AS M401 ");
			strRetSQL.append(" LEFT JOIN ma_genryokojo AS M402_1 ");
			strRetSQL.append(" ON  M401.cd_kaisha = M402_1.cd_kaisha ");
			strRetSQL.append(" AND M401.cd_genryo = M402_1.cd_genryo ");
			strRetSQL.append("WHERE ");
			strRetSQL.append(" M401.cd_kaisha = " + reqData.getFieldVale(0, 0, "cd_new_kaisha") + " ");
			strRetSQL.append("AND M401.cd_genryo IN (" + strGenryoVar + ") ");
			strRetSQL.append("AND M402_1.cd_busho = " + reqData.getFieldVale(0, 0, "cd_new_busho") + " ");

		} catch (Exception e) {
			this.em.ThrowException(e, "���/�H��􂢊����c�a���������Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜

		}
		//�A:�쐬����SQL��ԋp����B
		return strRetSQL;

	}
	//2011/05/30 QP@10181_No.66 TT T.Satoh Add End ---------------------------

	/**
	 * ����CD�������ɕϊ�
	 * @param reqData�@�F�肭�����ƂŁ[��
	 * @return�@�F�@����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeGenryoVar(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		boolean flg = true;

		try{

			for(int ix = 0; ix < reqData.getCntRow(0); ix++){

				if (flg){
					flg = false;

				}else{
					ret += ",";
				}
				ret += "'" + reqData.getFieldVale(0, ix, "cd_genryo") + "'";

			}

		}catch (Exception e) {
			this.em.ThrowException(e, "�����̏����w�蕶����̐����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜

		}


		return ret;

	}

// 20160513  KPX@1600766 ADD start
	/**
	 * �P���J�������擾
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return boolean  : �P���J������ �itrue�F�P���J������  false�F�P���J���s�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private boolean getTankaHyouji_kengen(RequestResponsKindBean reqData
			,UserInfoData _userInfoData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		boolean ret = false;			//�P���J���s��

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���e�����}�X�^�����N���X
		LiteralDataSearchLogic clsLiteralSearch = null;
		//���e�����}�X�^���N�G�X�g
		RequestResponsKindBean reqLiteral = null;
		//���e�����}�X�^�������X�|���X
		RequestResponsKindBean resLiteral = null;

		String value1 = "";

		try {
			//���N�G�X�g�C���X�^���X
			reqLiteral = new RequestResponsKindBean();
			//���e�����}�X�^�������N�G�X�g����
			reqLiteral.addFieldVale("table", "rec", "cd_category", "K_tanka_hyoujigaisha");
			//��ЃR�[�h
			reqLiteral.addFieldVale("table", "rec", "cd_literal", reqData.getFieldVale(0, 0, "cd_new_kaisha"));

			//���e�����}�X�^�����F�P���J������
			clsLiteralSearch = new LiteralDataSearchLogic();
			resLiteral = clsLiteralSearch.ExecLogic(reqLiteral, userInfoData);

			if (resLiteral != null) {
				value1 = toString(resLiteral.getFieldVale(0, 0, "value1"));
			}

			if (value1.equals("1") || value1.equals("9")) {
				ret = true;		//�P���J������
			}

		} catch (Exception e) {
			//�Y���f�[�^�����̎��͊J���s��
			ret = false;

		} finally {

		}
		return ret;
	}
// 20160513  KPX@1600766 ADD end


	/**
	 * ���X�|���X�f�[�^�𐶐�����
	 * @param lstGenryoData : �������ʏ�񃊃X�g
	 * @param kengen  �F�P���J�������itrue�F���@false�F�J���s�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(List<?> lstData
			//2011/06/06 QP@10181_No.66 TT T.Satoh Add Start -------------------------
			, List<?> kenkyujoLstData
			//2011/06/06 QP@10181_No.66 TT T.Satoh Add End ---------------------------
			, RequestResponsTableBean resTable
			, RequestResponsKindBean reqData
			// 20160513  KPX@1600766 ADD end
			, boolean kengen
			// 20160513  KPX@1600766 ADD end
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		Object[] items = null;
		Object[] tgItems = null;
		//2011/06/06 QP@10181_No.66 TT T.Satoh Add Start -------------------------
		Object[] kenkyujoItems = null;
		boolean gaitoFlg = false;
		//2011/06/06 QP@10181_No.66 TT T.Satoh Add End ---------------------------

		try {

			//���N�G�X�g�̌������A���X�|���X�𐶐�����
			for(int ix = 0; ix < reqData.getCntRow(0); ix++){

				//���N�G�X�g���ꂽ�����ɊY������f�[�^����������
				for (int i = 0; i < lstData.size(); i++) {

					items = (Object[]) lstData.get(i);
					tgItems = null;

					//2011/06/06 QP@10181_No.66 TT T.Satoh Change Start -------------------------
//					if (toString(reqData.getFieldVale(0, ix, "cd_genryo")).equals(toString(items[0]))){
//						//�Y������f�[�^������ꍇ
//						tgItems = items;
//						break;
//
//					}

					//�������̌����f�[�^���Ȃ������ꍇ
					if (kenkyujoLstData == null) {
						if (toString(reqData.getFieldVale(0, ix, "cd_genryo")).equals(toString(items[0]))){
						//�Y������f�[�^������ꍇ
						tgItems = items;
						break;

						}
					}
					//�������̌����f�[�^���������ꍇ
					else {
						//�������̌����f�[�^�̔z������[�v
						for (int j = 0; j < kenkyujoLstData.size(); j++) {

							//�������̌����f�[�^�̔z���1�s�擾
							kenkyujoItems = (Object[]) kenkyujoLstData.get(j);

							//�����R�[�h����v����ꍇ
							if (toString(reqData.getFieldVale(0, ix, "cd_genryo")).equals(toString(items[0]))
									&& items[0].toString().equals(kenkyujoItems[0].toString())) {
								//�����f�[�^�̔z����e����ւ�
								tgItems = kenkyujoItems;
								gaitoFlg = true;
								break;

							}

						}

						if (gaitoFlg) {
							//�����f�[�^�̓��e�����ւ��Ă����ꍇ

						}
						else if (toString(reqData.getFieldVale(0, ix, "cd_genryo")).equals(toString(items[0]))){
							//�Y������f�[�^������ꍇ
							tgItems = items;
							gaitoFlg = true;
							break;

						}
					}

					//�Y������f�[�^���������ꍇ
					if (gaitoFlg) {
						//���[�v�𔲂���
						break;
					}
					//2011/06/06 QP@10181_No.66 TT T.Satoh Change End ---------------------------

				}

				//2011/06/06 QP@10181_No.66 TT T.Satoh Add Start -------------------------
				gaitoFlg = false;
				//2011/06/06 QP@10181_No.66 TT T.Satoh Add End ---------------------------

				//������
				resTable.addFieldVale("rec" + ix, "sort_genryo"
						, reqData.getFieldVale(0, ix, "sort_genryo"));
				//����CD
				resTable.addFieldVale("rec" + ix, "cd_genryo"
						, reqData.getFieldVale(0, ix, "cd_genryo"));
				//���CD
				resTable.addFieldVale("rec" + ix, "cd_kaisha"
						, reqData.getFieldVale(0, ix, "cd_new_kaisha"));
				//����CD�i�H��CD�j
				//�V�K�����̏ꍇ��0�}��
				if(reqData.getFieldVale(0, ix, "cd_busho").equals("0")){
					resTable.addFieldVale("rec" + ix, "cd_busho"	, "0");
				}else{
					resTable.addFieldVale("rec" + ix, "cd_busho"
							, reqData.getFieldVale(0, ix, "cd_new_busho"));
				}


				if (tgItems == null){
					//����ЂɌ��������݂��Ȃ��ꍇ

					//��������
					resTable.addFieldVale("rec" + ix, "nm_genryo"
							, "��" + reqData.getFieldVale(0, ix, "nm_genryo"));
					//�P��
					resTable.addFieldVale("rec" + ix, "tanka"
							, reqData.getFieldVale(0, ix, "tanka"));
					//����
					resTable.addFieldVale("rec" + ix, "budomari"
							, reqData.getFieldVale(0, ix, "budomari"));
					//���ܗL��
					resTable.addFieldVale("rec" + ix, "ritu_abura"
							, reqData.getFieldVale(0, ix, "ritu_abura"));
					//�|�_
					resTable.addFieldVale("rec" + ix, "ritu_sakusan"
							, reqData.getFieldVale(0, ix, "ritu_sakusan"));
					//�H��
					resTable.addFieldVale("rec" + ix, "ritu_shokuen"
							, reqData.getFieldVale(0, ix, "ritu_shokuen"));
					//���_
					resTable.addFieldVale("rec" + ix, "ritu_sousan"
							, reqData.getFieldVale(0, ix, "ritu_sousan"));

				}else{
					//��ЂɌ��������݂���ꍇ

					if(toString(tgItems[3]) == ""){
						//���H��ɑ��݂��Ȃ��ꍇ

						//��������
						String mark = "";
						//�V�K�����̏ꍇ�Ɂ����Ȃ�
						if(toString(tgItems[3]).equals("0")){

						}else{
							mark = "��";
						}

						resTable.addFieldVale("rec" + ix, "nm_genryo"
								, mark + toString(tgItems[4]));
						//�P��
						resTable.addFieldVale("rec" + ix, "tanka"
								, reqData.getFieldVale(0, ix, "tanka"));
						//����
						resTable.addFieldVale("rec" + ix, "budomari"
								, reqData.getFieldVale(0, ix, "budomari"));

					}else{
						//���H��ɑ��݂���ꍇ

						//��������
						resTable.addFieldVale("rec" + ix, "nm_genryo"
								, toString(tgItems[3]));
						//�P��
						resTable.addFieldVale("rec" + ix, "tanka"
								, toString(tgItems[5]));
						//����
						resTable.addFieldVale("rec" + ix, "budomari"
								, toString(tgItems[6]));

					}

					//���ܗL��
					resTable.addFieldVale("rec" + ix, "ritu_abura"
							, toString(tgItems[7]));
					//�|�_
					resTable.addFieldVale("rec" + ix, "ritu_sakusan"
							, toString(tgItems[8]));
					//�H��
					resTable.addFieldVale("rec" + ix, "ritu_shokuen"
							, toString(tgItems[9]));
					//���_
					resTable.addFieldVale("rec" + ix, "ritu_sousan"
							, toString(tgItems[10]));

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.14
					//�V�K�����̏ꍇ�͒P���E�����͕ύX���Ȃ�
					if(toString(tgItems[2]).equals("0")){
						//�P��
						resTable.addFieldVale("rec" + ix, "tanka"
								, reqData.getFieldVale(0, ix, "tanka"));
						//����
						resTable.addFieldVale("rec" + ix, "budomari"
								, reqData.getFieldVale(0, ix, "budomari"));
					}
//mod end --------------------------------------------------------------------------------

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
					//�}�X�^����
					resTable.addFieldVale("rec" + ix, "ma_budomari"
							, toString(tgItems[11]));
//mod end --------------------------------------------------------------------------------

// 20160513  KPX@1600766 ADD start
					if (kengen == false) {
						//�P���E�������J���s�̎��A�󔒂ɂ���
						resTable.setFieldVale("rec" + ix, "tanka", "");
						resTable.setFieldVale("rec" + ix, "budomari", "");
						resTable.setFieldVale("rec" + ix, "ma_budomari", "");
					}
// 20160513  KPX@1600766 ADD end

				}

				//���ʂ̃��X�|���X�i�����̎��s��ԁj

				//�������ʇ@ ������
				resTable.addFieldVale("rec" + ix, "flg_return"
						, "true");
				//�������ʇA ���b�Z�[�W
				resTable.addFieldVale("rec" + ix, "msg_error"
						, "");
				//�������ʇB ��������
				resTable.addFieldVale("rec" + ix, "no_errmsg"
						, "");
				//�������ʇE ���b�Z�[�W�ԍ�
				resTable.addFieldVale("rec" + ix, "nm_class"
						, "");
				//�������ʇC �G���[�R�[�h
				resTable.addFieldVale("rec" + ix, "cd_error"
						, "");
				//�������ʇD �V�X�e�����b�Z�[�W
				resTable.addFieldVale("rec" + ix, "msg_system"
						, "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "���/�H��􂢊������X�|���X�f�[�^�̐����Ɏ��s���܂����B");

		} finally {
			tgItems = null;

		}

	}

}
