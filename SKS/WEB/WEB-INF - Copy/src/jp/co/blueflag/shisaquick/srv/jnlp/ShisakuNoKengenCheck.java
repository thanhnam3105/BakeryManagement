package jp.co.blueflag.shisaquick.srv.jnlp;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * 
 * ����No�����`�F�b�N����
 *  : ����R�[�h�̌����`�F�b�N���s���B
 * @author yoshida
 * @since  2009/08/03
 */
public class ShisakuNoKengenCheck extends LogicBase{ 
	/**
	 * ����No�����`�F�b�N�����p�R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public ShisakuNoKengenCheck(){
		//���N���X�̃R���X�g���N�^
		super();
	}
	/**
	 * ����No�����`�F�b�N����DB�`�F�b�N
	 * :����R�[�h�̌����`�F�b�N�����̊Ǘ����s���B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param _userInfoData : ���[�U�[���
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public boolean execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//JWS�N���t���O
		boolean flg_open = true;
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
		
		//������
		StringBuffer strSql = new StringBuffer();
		StringBuffer strSql_etr = new StringBuffer();
		StringBuffer strSql_hon = new StringBuffer();
		String strGamenId = null;
		String strKinoId = null;
		String strDataId = null;
		List<?> lstRecset = null;
		List<?> etrRecset = null;
		List<?> honRecset = null;
		
		try {
			
			//���ID�̎擾
			strGamenId = reqData.getFieldVale(0, 0, "mode");
			
			//�@�\ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strGamenId)){
					//�@�\ID��ݒ�
					strKinoId = userInfoData.getId_kino().get(i).toString();
				}
			}
			
			//�f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strGamenId)){
					//�f�[�^ID��ݒ�
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//�f�[�^�ҏW(�X�V)�̌���������ꍇ�A�f�[�^���擾����SQL���쐬
			strSql = createSQL(reqData);
			
			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//����f�[�^���(�ڍ�)�F�{���@�łȂ��A�������ʁi�f�[�^�ҏW�����j���Ȃ��ꍇ
			if (!(strGamenId.equals("100") && strKinoId.equals("10")) && lstRecset.size() == 0){
				
				//�f�[�^�{���̌���������ꍇ�A�f�[�^���擾����SQL���쐬
				strSql_etr = createSQL_etr(reqData);
				
				//SQL�����s
				super.createSearchDB();
				etrRecset = searchDB.dbSearch(strSql_etr.toString());
				
				//���O���[�v�̃f�[�^�łȂ��ꍇ
				if(etrRecset.size() == 0){
					
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start						
//					//�\���������Ȃ��ׁAJWS���N�����Ȃ�
//					flg_open = false;					
					
					//����O���[�v���i�{�l�{���ذ�ވȏ�j�y�{�l�ł���΃R�s�[�z �̏ꍇ
					if(strDataId.equals("5")){
						
						//�{�l�쐬�m�F�p��SQL���쐬
						strSql_hon = createSQL_honnin(reqData);
						
						//SQL�����s
						super.createSearchDB();
						honRecset = searchDB.dbSearch(strSql_hon.toString());
						
						//�{�l�쐬�̃f�[�^�łȂ��ꍇ
						if(honRecset.size() == 0){
							
							//�\���������Ȃ��ׁAJWS���N�����Ȃ�
							flg_open = false;
							
						}
						//�{�l�쐬�̃f�[�^�̏ꍇ
						else{
							//����R�s�[���[�h�ɕύX�i���O���[�v���ł���Ύ���R�s�[�̂݉\�j
							reqData.setFieldVale(0, 0, "mode","130");					
						}
						
					}
					//��L�ȊO�̏ꍇ
					else{
						
						//�\���������Ȃ��ׁAJWS���N�����Ȃ�
						flg_open = false;	
						
					}
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start						
				}
				//���O���[�v�̃f�[�^�̏ꍇ
				else{
					
					//����O���[�v���i�{�l�{���ذ�ވȏ�j�y�{�l�ł���΃R�s�[�z �̏ꍇ
					if(strDataId.equals("5")){
						
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start	
						//����R�s�[���[�h�ɕύX�i���O���[�v���ł���Ύ���R�s�[�̂݉\�j
						reqData.setFieldVale(0, 0, "mode","130");					
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end	
						
					}
					//��L�ȊO�̏ꍇ
					else{
						//�Q�ƃ��[�h�ɕύX�i���O���[�v���ł���ΎQ�Ƃ̂݉\�j
						reqData.setFieldVale(0, 0, "mode","000");
					}
				}
			}
			//����f�[�^���(�ڍ�)�F�{���@�ŁA�A�������ʁi�f�[�^�ҏW�����j���Ȃ��ꍇ
			else if((strGamenId.equals("100") && strKinoId.equals("10"))&& lstRecset.size() == 0){
				
				flg_open = false;
			
			}
			//����f�[�^���(�ڍ�)�F�{���@�ŁA�A�������ʁi�f�[�^�ҏW�����j������ꍇ
			else if((strGamenId.equals("100") && strKinoId.equals("10"))&& lstRecset.size() != 0){
				
				//�Q�ƃ��[�h�ɕύX
				reqData.setFieldVale(0, 0, "mode","000");
			}
			
		}
		catch (Exception e) {
			this.em.ThrowException(e, "����R�[�h�̌����`�F�b�N�����Ɏ��s���܂����B");
		}
		finally {
			//���X�g�̔j��
			removeList(lstRecset);
			removeList(etrRecset);
			removeList(honRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			//�ϐ��̍폜
			strSql = null;
			strSql_etr = null;
			strSql_hon = null;
		}
		return flg_open;
	}
	/**
	 * ����No�����`�F�b�N����SQL�쐬
	 *  : �f�[�^�ҏW(�X�V)�̌���������ꍇ�A�f�[�^���擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @return strRetSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	StringBuffer strRetSql = new StringBuffer();
	String strDataId = null;
	String strGamenId = null;
	String strNo_shisaku = null;
	String strCd_shain = null;
	String strNen = null;
	String strNo_oi = null;
	
	try {
		//�S����Ђ�SQL�ݒ�
		String strSqlTanto = "SELECT Shin.cd_kaisha FROM tr_shisakuhin Shin JOIN ma_tantokaisya Tanto ON Shin.cd_kaisha = Tanto.cd_tantokaisha AND Tanto.id_user = " + userInfoData.getId_user();
		//���ID�̎擾
		strGamenId = reqData.getFieldVale(0, 0, "mode");
		//�@�\���N�G�X�g�f�[�^�̎���R�[�h�̒l���擾����
		strNo_shisaku = reqData.getFieldVale(0, 0, "no_shisaku");
		
		//����R�[�h���Ј�CD �N �ǔԂɕ�������
		strCd_shain = strNo_shisaku.split("-")[0];
		strNen = strNo_shisaku.split("-")[1];
		strNo_oi = strNo_shisaku.split("-")[2];
		
		//�����擾
		for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
			if (userInfoData.getId_gamen().get(i).toString().equals(strGamenId)){
				//�f�[�^ID��ݒ�
				strDataId = userInfoData.getId_data().get(i).toString();
			}
		}
		
		//SQL�̍쐬
		strRetSql.append(" SELECT T1.*");
		strRetSql.append(" FROM tr_shisakuhin T1 ");
		strRetSql.append(" LEFT JOIN ma_user M2 ON T1.id_toroku = M2.id_user ");
		strRetSql.append(" Where");
		strRetSql.append(" T1.cd_shain=" + strCd_shain);
		strRetSql.append(" And");
		strRetSql.append(" T1.nen=" + strNen);
		strRetSql.append(" And");
		strRetSql.append(" T1.no_oi=" + strNo_oi);

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start						
		//�������������ݒ�
		//����O���[�v���i�{�l+���ذ�ް�ȏ�j
		//if (strDataId.equals("1")){
		//����O���[�v���i�{�l+���ذ�ް�ȏ�j or ����O���[�v���i�{�l�{���ذ�ވȏ�j�y�{�l�ł���΃R�s�[�z
	    if (strDataId.equals("1") || strDataId.equals("5")){
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end
	    	
			strRetSql.append(" AND");
			strRetSql.append(" ( ");
			strRetSql.append(" ( ");
			strRetSql.append(" T1.cd_group = ");
			strRetSql.append(userInfoData.getCd_group());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_team = ");
			strRetSql.append(userInfoData.getCd_team());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.id_toroku = ");
			strRetSql.append(userInfoData.getId_user());				
			strRetSql.append(" ) ");

			
		// 2010.10.20 Mod Arai Start
		// �O���[�v���[�_�[�́A����O���[�v�̑S�`�[����ҏW�\
			//�Z�b�V�������D��E�R�[�h���O���[�v���[�_�[�̏ꍇ�A�ȉ���OR�����Œǉ�
			if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "GROUP_LEADER_CD"))
					== Integer.parseInt(userInfoData.getCd_literal())){
				strRetSql.append(" OR ");				
				strRetSql.append(" ( ");
				strRetSql.append(" T1.cd_group = ");
				strRetSql.append(userInfoData.getCd_group());
				strRetSql.append(" AND ");
				//strRetSql.append(" M2.cd_yakushoku <= '");
				strRetSql.append(" ISNULL(M2.cd_yakushoku,'001') <= '");
				strRetSql.append(userInfoData.getCd_literal());
				strRetSql.append("' ) ");
			}
			//�Z�b�V�������D��E�R�[�h���`�[�����[�_�[�̏ꍇ�A�ȉ���OR�����Œǉ�
			if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "TEAM_LEADER_CD"))
					== Integer.parseInt(userInfoData.getCd_literal())){
				strRetSql.append(" OR ");				
				strRetSql.append(" ( ");
				strRetSql.append(" T1.cd_group = ");
				strRetSql.append(userInfoData.getCd_group());
				strRetSql.append(" AND ");
				strRetSql.append(" T1.cd_team = ");
				strRetSql.append(userInfoData.getCd_team());
				strRetSql.append(" AND ");
				//strRetSql.append(" M2.cd_yakushoku <= '");
				strRetSql.append(" ISNULL(M2.cd_yakushoku,'001') <= '");
				strRetSql.append(userInfoData.getCd_literal());
				strRetSql.append("' ) ");
			}
//			//�Z�b�V�������D��E�R�[�h���`�[�����[�_�[�̏ꍇ�A�ȉ���OR�����Œǉ�
//			if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "TEAM_LEADER_CD"))
//					<= Integer.parseInt(userInfoData.getCd_literal())){
//				strRetSql.append(" OR ");				
//				strRetSql.append(" ( ");
//				strRetSql.append(" T1.cd_group = ");
//				strRetSql.append(userInfoData.getCd_group());
//				strRetSql.append(" AND ");
//				strRetSql.append(" T1.cd_team = ");
//				strRetSql.append(userInfoData.getCd_team());
//				strRetSql.append(" AND ");
//				strRetSql.append(" M2.cd_yakushoku <= '");
//				strRetSql.append(userInfoData.getCd_literal());
//				strRetSql.append("' ) ");
//			}
		// 2010.10.20 Mod Arai End
			strRetSql.append(" ) ");
			
		//����O���[�v���S�����
		} else if (strDataId.equals("2")){
			strRetSql.append(" AND");
			strRetSql.append(" ( ");
			strRetSql.append(" T1.cd_group = ");
			strRetSql.append(userInfoData.getCd_group());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_kaisha in ( ");
			strRetSql.append(strSqlTanto + " ) ");
			strRetSql.append(" ) ");

		//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
		} else if (strDataId.equals("3")){
			strRetSql.append(" AND");
			strRetSql.append(" ( ");
			strRetSql.append(" T1.cd_group = ");
			strRetSql.append(userInfoData.getCd_group());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_team = ");
			strRetSql.append(userInfoData.getCd_team());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.id_toroku = ");
			strRetSql.append(userInfoData.getId_user());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_kaisha in ( ");
			strRetSql.append(strSqlTanto);
			strRetSql.append(" ) ");

			//�Z�b�V�������D��E�R�[�h���`�[�����[�_�[�̏ꍇ�A�ȉ���OR�����Œǉ�
			if (Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�, "TEAM_LEADER_CD"))
					<= Integer.parseInt(userInfoData.getCd_literal())){

				strRetSql.append(" OR ");
				strRetSql.append(" ( ");
				strRetSql.append(" T1.cd_group = ");
				strRetSql.append(userInfoData.getCd_group());
				strRetSql.append(" AND ");
				strRetSql.append(" T1.cd_team = ");
				strRetSql.append(userInfoData.getCd_team());
				strRetSql.append(" AND ");
				strRetSql.append(" M2.cd_group = ");
				strRetSql.append(userInfoData.getCd_group());
				strRetSql.append(" AND ");
				strRetSql.append(" M2.cd_team = ");
				strRetSql.append(userInfoData.getCd_team());
				strRetSql.append(" AND ");
				strRetSql.append(" M2.cd_yakushoku <= '");
				strRetSql.append(userInfoData.getCd_literal());
				strRetSql.append("' AND ");
				strRetSql.append(" T1.cd_kaisha in ( ");
				strRetSql.append(strSqlTanto);
				strRetSql.append(" ) ");
				strRetSql.append(" ) ");
			}
			
			strRetSql.append(" ) ");
		//���H�ꕪ
		} else if (strDataId.equals("4")){
			strRetSql.append(" AND");
			strRetSql.append(" ( ");
			strRetSql.append(" T1.cd_kaisha = ");
			strRetSql.append(userInfoData.getCd_kaisha());
			strRetSql.append(" AND ");
			strRetSql.append(" T1.cd_kojo = ");
			strRetSql.append(userInfoData.getCd_busho());
			strRetSql.append(" ) ");
		}

		//�������u���H�ꕪ�v�ȊO�̏ꍇ
		if (!strDataId.equals("4")){
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start	
			//����A�L���[�s�[�̕��̂݃V�T�N�C�b�N���g�p����ׁA���L�������R�����g�A�E�g
//			strRetSql.append(" AND");
//			strRetSql.append(" T1.cd_shain IN ");
//			strRetSql.append(" (SELECT id_user FROM ma_user WHERE cd_kaisha = " + userInfoData.getCd_kaisha() + ")");
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end
		}
		
	} catch (Exception e) {
		this.em.ThrowException(e, "�����`�F�b�NSQL�̍쐬�Ɏ��s���܂����B");
	} finally {
	}	
		return strRetSql;
	}
	/**
	 * ����No�{�������`�F�b�N����SQL�쐬
	 *  : �f�[�^�{���̌���������ꍇ�A�f�[�^���擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @return strRetSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSQL_etr(RequestResponsKindBean reqData) 
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
			strRetSql.append(" T1.cd_group = " + userInfoData.getCd_group());
		} catch (Exception e) {
			this.em.ThrowException(e, "�����`�F�b�NSQL�̍쐬�Ɏ��s���܂����B");
		} finally {
		}	
		return strRetSql;
	}
	
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start	
	/**
	 * ����No�{�������`�F�b�N����SQL�쐬
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
			this.em.ThrowException(e, "�����`�F�b�NSQL�̍쐬�Ɏ��s���܂����B");
		} finally {
		}	
		return strRetSql;
	}
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end
}