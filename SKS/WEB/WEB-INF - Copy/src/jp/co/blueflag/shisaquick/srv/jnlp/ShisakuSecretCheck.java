package jp.co.blueflag.shisaquick.srv.jnlp;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ����No�V�[�N���b�g���[�h�`�F�b�N����
 *  : ����R�[�h�̃V�[�N���b�g���[�h�̃`�F�b�N���s���B
 * @author shima
 * @since  2012/10/16
 */
public class ShisakuSecretCheck extends LogicBase {
	/**
	 * ����No�����`�F�b�N�����p�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public ShisakuSecretCheck(){
		//���N���X�̃R���X�g���N�^
		super();
	}

	/**
	 * ����No�V�[�N���b�g���[�h�`�F�b�N����DB�`�F�b�N
	 * :����R�[�h�̃V�[�N���b�g���[�h�̃`�F�b�N�����̊Ǘ����s���B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param _userInfoData : ���[�U�[���
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public boolean execDataCheck(
		RequestResponsKindBean reqData,
		UserInfoData _userInfoData
		)
	throws ExceptionSystem,ExceptionUser,ExceptionWaning{
		
		//JWS�N���t���O
		boolean flg_open = true;
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
		
		//������
		StringBuffer strSql = new StringBuffer();
		StringBuffer strSql_hon = new StringBuffer();
		StringBuffer strSql_team = new StringBuffer();
		List<?> lstRecset = null;
		List<?> honRecset = null;
		List<?> teamRecset = null;
		
		try{
			strSql = createSQL(reqData);
			
			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ�������(�V�[�N���b�g���[�h)�̏ꍇ
			if(lstRecset.size() == 0){
				
				//�o�^�҂�
				strSql_hon = createSQL_honnin(reqData);
				honRecset = searchDB.dbSearch(strSql_hon.toString());
				if(honRecset.size() == 0){
					
					//�����`�[����
					strSql_team = createSQL_team(reqData);
					teamRecset = searchDB.dbSearch(strSql_team.toString());
					if(teamRecset.size() == 0){
					
						// 20160420 MOD KPX@1600766 Start
						//�`�[�����[�_�[ OR �O���[�v���[�_�[��
//						if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "GROUP_LEADER_CD"))
//							== Integer.parseInt(userInfoData.getCd_literal()) ||
//							Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "TEAM_LEADER_CD"))
//							== Integer.parseInt(userInfoData.getCd_literal())){
						//�O���[�v���[�_�[��
						if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "GROUP_LEADER_CD"))
								== Integer.parseInt(userInfoData.getCd_literal())){
						// 20160420 MOD KPX@1600766 End
						}else{
							flg_open = false;
						}
					}
				}
			}
		}
		catch(Exception e){
			this.em.ThrowException(e,"����R�[�h�̃V�[�N���b�g�`�F�b�N�����Ɏ��s���܂����B");
		}
		finally{
			//���X�g�̔j��
			removeList(lstRecset);
			removeList(honRecset);
			removeList(teamRecset);
			if(searchDB != null){
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			//�ϐ��̍폜
			strSql = null;
			strSql_hon = null;
			strSql_team = null;
		}
		return flg_open;
	}
	
	/**
	 * ����No�V�[�N���b�g�ݒ�`�F�b�N����SQL�쐬
	 *  : �V�[�N���b�g�ݒ肪����ꍇ�A�f�[�^���擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @return strRetSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer strRetSql = new StringBuffer();
		String strNo_shisaku = null;
		String strCd_shain = null;
		String strNen = null;
		String strNo_oi = null;
		
		try {
			//�@�\���N�G�X�g�f�[�^�̎���R�[�h�̒l���擾����
			strNo_shisaku = reqData.getFieldVale(0, 0, "no_shisaku");
			
			//����R�[�h���Ј�CD �N �ǔԂɕ�������
			strCd_shain = strNo_shisaku.split("-")[0];
			strNen = strNo_shisaku.split("-")[1];
			strNo_oi = strNo_shisaku.split("-")[2];
			
			//SQL�̍쐬
			strRetSql.append(" SELECT T1.*");
			strRetSql.append(" FROM tr_shisakuhin T1 ");
			strRetSql.append(" LEFT JOIN ma_user M2 ON T1.id_toroku = M2.id_user ");
			strRetSql.append(" WHERE");
			strRetSql.append(" T1.cd_shain=" + strCd_shain);
			strRetSql.append(" AND");
			strRetSql.append(" T1.nen=" + strNen);
			strRetSql.append(" AND");
			strRetSql.append(" T1.no_oi=" + strNo_oi);
			strRetSql.append(" AND");
			strRetSql.append(" T1.flg_secret IS NULL");
		}
		catch (Exception e) {
			this.em.ThrowException(e, "�V�[�N���b�g�`�F�b�NSQL�̍쐬�Ɏ��s���܂����B");
		} finally {
		}
		
		return strRetSql;
	}
	
	/**
	 * ����No�V�[�N���b�g�{�������`�F�b�N����SQL�쐬
	 *  : �{�l���쐬�������̂��ǂ����𔻒�
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @return strRetSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL_honnin(RequestResponsKindBean reqData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer strRetSql = new StringBuffer();
		String strNo_shisaku = null;
		String strCd_shain = null;
		String strNen = null;
		String strNo_oi = null;
		
		try {
			//�@�\���N�G�X�g�f�[�^�̎���R�[�h�̒l���擾����
			strNo_shisaku = reqData.getFieldVale(0, 0, "no_shisaku");
			
			//����R�[�h���Ј�CD �N �ǔԂɕ�������
			strCd_shain = strNo_shisaku.split("-")[0];
			strNen = strNo_shisaku.split("-")[1];
			strNo_oi = strNo_shisaku.split("-")[2];
			
			//SQL�̍쐬
			strRetSql.append(" SELECT T1.*");
			strRetSql.append(" FROM tr_shisakuhin T1 ");
			strRetSql.append(" Where");
			strRetSql.append(" T1.cd_shain=" + strCd_shain);
			strRetSql.append(" And");
			strRetSql.append(" T1.nen=" + strNen);
			strRetSql.append(" And");
			strRetSql.append(" T1.no_oi=" + strNo_oi);
			strRetSql.append(" And");
			strRetSql.append(" T1.cd_shain=" + userInfoData.getId_user());
		} catch (Exception e) {
			this.em.ThrowException(e, "�V�[�N���b�g�`�F�b�NSQL�̍쐬�Ɏ��s���܂����B");
		} finally {
		}	
		return strRetSql;
	}
	
	/**
	 * ����No�V�[�N���b�g�{�������`�F�b�N����SQL�쐬
	 *  : ���`�[���̎҂��쐬�������̂��ǂ����𔻒�
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @return strRetSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL_team(RequestResponsKindBean reqData) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{
			StringBuffer strRetSql = new StringBuffer();
			String strNo_shisaku = null;
			String strCd_shain = null;
			String strNen = null;
			String strNo_oi = null;
			
			try {
				//�@�\���N�G�X�g�f�[�^�̎���R�[�h�̒l���擾����
				strNo_shisaku = reqData.getFieldVale(0, 0, "no_shisaku");
				
				//����R�[�h���Ј�CD �N �ǔԂɕ�������
				strCd_shain = strNo_shisaku.split("-")[0];
				strNen = strNo_shisaku.split("-")[1];
				strNo_oi = strNo_shisaku.split("-")[2];
				
				//SQL�̍쐬
				strRetSql.append(" SELECT T1.*");
				strRetSql.append(" FROM tr_shisakuhin T1 ");
				strRetSql.append(" Where");
				strRetSql.append(" T1.cd_shain=" + strCd_shain);
				strRetSql.append(" And");
				strRetSql.append(" T1.nen=" + strNen);
				strRetSql.append(" And");
				strRetSql.append(" T1.no_oi=" + strNo_oi);
				strRetSql.append(" And");
				strRetSql.append(" T1.cd_group=" + userInfoData.getCd_group());
				strRetSql.append(" And");
				strRetSql.append(" T1.cd_team=" + userInfoData.getCd_team());
			} catch (Exception e) {
				this.em.ThrowException(e, "�V�[�N���b�g�`�F�b�NSQL�̍쐬�Ɏ��s���܂����B");
			} finally {
			}	
			return strRetSql;
		}
	
}
