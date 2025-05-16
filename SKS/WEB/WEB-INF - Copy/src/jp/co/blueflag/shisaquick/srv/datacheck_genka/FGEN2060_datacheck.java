package jp.co.blueflag.shisaquick.srv.datacheck_genka;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.math.BigDecimal;
import java.util.List;


/**
 * �yQP@00342�z
 * �S���҃}�X�^�����e�i�c�Ɓj�@�S���Ҍ����i�c�Ɓj�f�[�^�`�F�b�N
 *  �@�\ID�FFGEN2060
 *  
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2060_datacheck extends DataCheck{
	
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@�S���Ҍ����i�c�Ɓj�f�[�^�`�F�b�N
	 * : �C���X�^���X����
	 */
	public FGEN2060_datacheck() {
		//���N���X�̃R���X�g���N�^
		super();
	}
	
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@�S���Ҍ����i�c�Ɓj�f�[�^�`�F�b�N
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;
		
		try {
			//���N�G�X�g����f�[�^���o
			String id_user = reqData.getFieldVale(0, 0, "id_user");
			
			//�@�\ID�擾�p
			String strUKinoId = "";
			String strUDataId = "";
			
			//�c�Ɓi��ʁj�����R�[�h�擾
			String strEigyoIppan = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_IPPAN");
			//�c�Ɓi�{�������j�����R�[�h�擾
			String strEigyoHonbu = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_HONBU");
			//�c�Ɓi�V�X�e���Ǘ��ҁj�����R�[�h�擾
			String strEigyoSystem = 
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_SYSTEM");
			
			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("200")){
					//�@�\ID��ݒ�
					strUKinoId = userInfoData.getId_kino().get(i).toString(); 
					//�f�[�^ID��ݒ�
					strUDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			
			//�������u�����̂݁v�̏ꍇ
			if(strUKinoId.equals("100") && strUDataId.equals("1")){
				
				if(id_user.equals(userInfoData.getId_user())){
					
				}
				else{
					em.ThrowException(ExceptionKind.���Exception,"E000310","", "", "");
				}
				
			}
			//�������u�S�Ẳc�ƒS���ҁi��ʂ̂݁j�v�̏ꍇ
			else if(strUKinoId.equals("101") && strUDataId.equals("1")){
				
				//���[�U����
				strSql.append(" SELECT    ");
				strSql.append(" 	cd_kengen   ");
				strSql.append(" 	,cd_kaisha   ");
				strSql.append(" 	,cd_busho   ");
				strSql.append(" FROM ma_user   ");
				strSql.append(" WHERE id_user= "+id_user);
				
				// SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//�f�[�^�����݂��Ȃ��ꍇ�͂��̂܂�
				if (lstRecset.size() == 0){
					
				}
				//�f�[�^�����݂���ꍇ�ɃG���[�`�F�b�N
				else{
					//�������ʎ擾
					Object[] items = (Object[]) lstRecset.get(0);
					String cd_kengen = toString(items[0],"");
					
					//�c�ƒS���ҁi��ʁj�@�ȊO�̓G���[
					if(cd_kengen.equals(strEigyoIppan)){
						
					}
					else{
						em.ThrowException(ExceptionKind.���Exception,"E000311","", "", "");
					}
				}
			}
			//�������u�S�Ẳc�ƒS���ҁi��ʁA�{�������j�v�̏ꍇ
			else if(strUKinoId.equals("102") && strUDataId.equals("1")){
				//���[�U����
				strSql.append(" SELECT    ");
				strSql.append(" 	cd_kengen   ");
				strSql.append(" 	,cd_kaisha   ");
				strSql.append(" 	,cd_busho   ");
				strSql.append(" FROM ma_user   ");
				strSql.append(" WHERE id_user= "+id_user);
				
				// SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//�f�[�^�����݂��Ȃ��ꍇ�͂��̂܂�
				if (lstRecset.size() == 0){
					
				}
				//�f�[�^�����݂���ꍇ�ɃG���[�`�F�b�N
				else{
					//�������ʎ擾
					Object[] items = (Object[]) lstRecset.get(0);
					String cd_kengen = toString(items[0],"");
					// ADD 2013/11/7 QP@30154 okano start
					String cd_kaisha = toString(items[1],"");
					// ADD 2013/11/7 QP@30154 okano end
					
					//�����̏ꍇ
					if(id_user.equals(userInfoData.getId_user())){
						
					}
					//�����ȊO�̏ꍇ
					else{
						//�c�ƒS���ҁi��ʁj�ȊO�̓G���[
						// MOD 2013/11/7 QP@30154 okano start
//							if(cd_kengen.equals(strEigyoIppan)){
						if(cd_kengen.equals(strEigyoIppan) && cd_kaisha.equals(userInfoData.getCd_kaisha())){
						// MOD 2013/11/7 QP@30154 okano end
							
						}
						else{
							em.ThrowException(ExceptionKind.���Exception,"E000311","", "", "");
						}
					}
				}
				
			}
			//�������u�S�āi�c�Ɓj�v�̏ꍇ
			else if(strUKinoId.equals("103") && strUDataId.equals("1")){
				
				//���[�U����
				strSql.append(" SELECT    ");
				strSql.append(" 	cd_kengen   ");
				strSql.append(" 	,cd_kaisha   ");
				strSql.append(" 	,cd_busho   ");
				strSql.append(" FROM ma_user   ");
				strSql.append(" WHERE id_user= "+id_user);
				
				// SQL�����s
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//�f�[�^�����݂��Ȃ��ꍇ�͂��̂܂�
				if (lstRecset.size() == 0){
					
				}
				//�f�[�^�����݂���ꍇ�ɃG���[�`�F�b�N
				else{
					//�������ʎ擾
					Object[] items = (Object[]) lstRecset.get(0);
					String cd_kengen = toString(items[0],"");
					
					//�c�ƒS���ҁ@�ȊO�̓G���[
					if( cd_kengen.equals(strEigyoIppan) 
							|| cd_kengen.equals(strEigyoHonbu) 
							|| cd_kengen.equals(strEigyoSystem)  ){
						
					}
					else{
						em.ThrowException(ExceptionKind.���Exception,"E000313","", "", "");
					}
				}
				
			}
			
			
		} catch (Exception e) {
			
			this.em.ThrowException(e, "�S���҃}�X�^�����e�i�c�Ɓj�@�S���Ҍ����i�c�Ɓj�f�[�^�`�F�b�N�����Ɏ��s���܂����B");
			
		} finally {
			
			//���X�g�̔j��
			removeList(lstRecset);
			
			//�Z�b�V�����̃N���[�Y
			if (searchBD != null) {
				searchBD.Close();
				searchBD = null;
			}
			
			//�ϐ��̍폜
			strSql = null;
			
		}
	}
}
