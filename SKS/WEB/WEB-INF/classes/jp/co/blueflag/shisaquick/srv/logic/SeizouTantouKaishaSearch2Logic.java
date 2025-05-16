package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * �����S����Вǉ��F�����S����Ќ���DB����
 *  : �����S����Вǉ��F�����S����Џ�����������B
 * @author jinbo
 * @since  2009/04/08
 */
public class SeizouTantouKaishaSearch2Logic extends LogicBase{

	/**
	 * �����S����Ќ����R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public SeizouTantouKaishaSearch2Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * �����S����Вǉ��F�����S����Ѓf�[�^�擾SQL�쐬
	 *  : �����S����Ѓf�[�^���擾����SQL���쐬�B
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
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�e�[�u��Bean�擾
			RequestResponsTableBean reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
			//�sBean�擾
			RequestResponsRowBean reqRecBean = (RequestResponsRowBean)reqTableBean.GetItem(0);
			
			//SQL���̍쐬
			strSql = seizouTantouKaisha2DataCreateSQL(reqData, strSql);
			
			//DB�C���X�^���X����
			createSearchDB();
			//�������s
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}

			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

			//���X�|���X�f�[�^�̌`��
			storageSeizouTantouKaisha2Data(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����S����Ќ���DB�����Ɏ��s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSql = null;
		}
		return resKind;
	}

	/**
	 * �����S����Џ��擾SQL�쐬
	 *  : �����S����Џ����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer seizouTantouKaisha2DataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strAllSql = new StringBuffer();

		try {
			String strKaishaName = null;
			String strPageNo = null;
			String dataId = null;
			
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			//��Ж��̎擾
			strKaishaName = reqData.getFieldVale(0, 0, "nm_kaisha");
			//�I���y�[�WNo�̎擾
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("90")){
					//�S���҃}�X�^�����e�i���X��ʂ̃f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//SQL���̍쐬	
			strAllSql.append("SELECT");
			strAllSql.append("	tbl.no_row ");
			strAllSql.append(" ,tbl.cd_kaisha");
			strAllSql.append(" ,tbl.nm_kaisha");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append("	,cnttbl.max_row ");
			strAllSql.append(" FROM (");

			strSql.append("SELECT");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY cd_kaisha)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY cd_kaisha)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY cd_kaisha)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,RIGHT('00000' + CONVERT(varchar,cd_kaisha),5) as cd_kaisha");
			strSql.append(" ,nm_kaisha");
			strSql.append(" FROM (SELECT cd_kaisha, nm_kaisha FROM ma_busho GROUP BY cd_kaisha, nm_kaisha) M104 ");

			//��Ж������͂���Ă���ꍇ
			if (!strKaishaName.equals("")) {
				strSql.append(" WHERE nm_kaisha LIKE '%");
				strSql.append(strKaishaName);
				strSql.append("%'");
			}
			
			//�������������ݒ�
			//�����̂�
			if(dataId.equals("1") || dataId.equals("9")) {
				//�����Ȃ�
			}
			
			strAllSql.append(strSql);
			strAllSql.append("	) AS tbl ");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + strPageNo);
			strAllSql.append(" ORDER BY ");
			strAllSql.append(" tbl.cd_kaisha ");
			
			strSql = strAllSql;
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����S����Ќ���DB�����Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
			strAllSql = null;
		}
		return strSql;
	}
	
	/**
	 * �����S����Вǉ��F�����S����Ѓf�[�^�p�����[�^�[�i�[
	 *  : �����S����Ѓf�[�^�������X�|���X�f�[�^�֊i�[����B
	 * @param lstSeizouTantouShaData2 : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeizouTantouKaisha2Data(List<?> lstSeizouTantouShaData2, RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstSeizouTantouShaData2.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstSeizouTantouShaData2.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "no_row", items[0].toString());
				resTable.addFieldVale(i, "cd_kaisha", items[1].toString());
				resTable.addFieldVale(i, "nm_kaisha", items[2].toString());
				resTable.addFieldVale(i, "list_max_row", items[3].toString());
				resTable.addFieldVale(i, "max_row", items[4].toString());

			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����S����Ќ���DB�����Ɏ��s���܂����B");

		} finally {

		}

	}

}
