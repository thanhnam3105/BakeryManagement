package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * [S3-53 SA580] ����R�[�h���͌����c�a����
 * @author TT.katayama
 * @since 2009/04/08
 *
 */
public class InputGenryoSearchLogic extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public InputGenryoSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ����R�[�h���̓f�[�^�擾����
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

		//���N�G�X�g
		String strReqKaishaCd = "";
		String strReqBushoCd = "";
		String strReqGenryoCd = "";

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�F���N�G�X�g�f�[�^���A����R�[�h���͌��������𒊏o���A����R�[�h���͌����f�[�^�����擾����SQL���쐬����B

			//�@�\���N�G�X�g�f�[�^���擾
			strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strReqBushoCd = reqData.getFieldVale(0, 0, "cd_busho");
			strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			
			//SQL���̍쐬
			strSql = createSQL(strReqKaishaCd,strReqBushoCd,strReqGenryoCd);
			
			//�A�F�f�[�^�x�[�X������p���A���쌴���f�[�^���擾����B
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());
			
			//�B�F���쌴���f�[�^�p�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageInputGenryoData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����R�[�h���̓f�[�^�擾���������s���܂����B");
			
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
	 * �����pSQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h�@
	 * @param strBushoCd : �����R�[�h
	 * @param strGenryoCd : �����R�[�h
	 * @return �쐬����SQL
	 */
	private StringBuffer createSQL(String strKaishaCd, String strBushoCd, String strGenryoCd) {
		StringBuffer strRetSql = new StringBuffer();;
		
		//�@�F����R�[�h���͏����擾���邽�߂�SQL���쐬
		strRetSql.append(" SELECT  ");
		strRetSql.append("   M401.cd_genryo AS cd_genryo ");
		strRetSql.append("  ,M402.nm_genryo AS nm_genryo ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
		strRetSql.append(" FROM ma_genryo M401 ");
		strRetSql.append("  LEFT JOIN ma_genryokojo M402 ");
		strRetSql.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
		strRetSql.append("   AND M402.cd_genryo = M401.cd_genryo ");
		strRetSql.append(" WHERE M401.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M402.cd_busho =" + strBushoCd);
		strRetSql.append("  AND M401.cd_genryo = '" + strGenryoCd + "' ");
		
		//�A:�쐬����SQL��ԋp
		return strRetSql;
	}
	
	/**
	 * ����R�[�h���̓f�[�^�p�����[�^�[�i�[
	 * @param lstGenryoData : �������ʏ�񃊃X�g
	 */
	private void storageInputGenryoData(List<?> lstGenryoData, RequestResponsTableBean resTable)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//�@�F�����@�������ʏ�񃊃X�g�ɕێ����Ă���e�p�����[�^�[�����X�|���X�f�[�^�֊i�[����B
		try {
			
			for (int i = 0; i < lstGenryoData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryoData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale("rec" + i, "cd_genryo", items[0].toString());
				resTable.addFieldVale("rec" + i, "nm_genryo", items[1].toString());
				resTable.addFieldVale("rec" + i, "tanka", items[2].toString());
				resTable.addFieldVale("rec" + i, "budomari", items[3].toString());
				resTable.addFieldVale("rec" + i, "ritu_abura", items[4].toString());
				resTable.addFieldVale("rec" + i, "ritu_sakusan", items[5].toString());
				resTable.addFieldVale("rec" + i, "ritu_shokuen", items[6].toString());
				resTable.addFieldVale("rec" + i, "ritu_sousan", items[7].toString());
				
			}

			if (lstGenryoData.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale("rec0", "flg_return", "true");
				resTable.addFieldVale("rec0", "msg_error", "");
				resTable.addFieldVale("rec0", "no_errmsg", "");
				resTable.addFieldVale("rec0", "nm_class", "");
				resTable.addFieldVale("rec0", "cd_error", "");
				resTable.addFieldVale("rec0", "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale("rec0", "cd_genryo", "");
				resTable.addFieldVale("rec0", "nm_genryo", "");
				resTable.addFieldVale("rec0", "tanka", "");
				resTable.addFieldVale("rec0", "budomari", "");
				resTable.addFieldVale("rec0", "ritu_abura", "");
				resTable.addFieldVale("rec0", "ritu_sakusan", "");
				resTable.addFieldVale("rec0", "ritu_shokuen", "");
				resTable.addFieldVale("rec0", "ritu_sousan", "");
				
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "����R�[�h���̓f�[�^�p�����[�^�[�i�[���������s���܂����B");

		} finally {

		}
		
	}
	
}
