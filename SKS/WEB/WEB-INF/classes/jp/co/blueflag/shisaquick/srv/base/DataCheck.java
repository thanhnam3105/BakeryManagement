package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;


/**
 * 
 * �f�[�^�`�F�b�N
 *  : �e���W�b�N�ADB�������s�O�f�[�^�`�F�b�N���s���B
 * @author TT.furuta
 * @since  2009/03/25
 */
public class DataCheck extends ObjectBase{

	//�f�[�^�x�[�X����
	protected SearchBaseDao searchBD;
	//���[�U�[���Ǘ�
	protected UserInfoData userInfoData = null;
	
	/**
	 * �R���X�g���N�^
	 *  : �f�[�^�`�F�b�N�R���X�g���N�^
	 * @param checkData : �����f�[�^
	 */
	public DataCheck() {
		super();

	}
	
	/**
	 * �f�[�^�`�F�b�N
	 *  : �e���W�b�N�@�E�@DB������s�O�̃f�[�^�`�F�b�N���s���B
	 * @param checkData : �`�F�b�N�Ώۃf�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean requestData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		//�e�N���X�ɂăI�[�o�[���C�h���Ďg�p

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

	}
	/**
	 * DB�̌����̐���
	 * �FDB�̌����׽�𐶐�����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void createSearchDB() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//DB�Z�b�V�����𐶐�
			searchBD = new SearchBaseDao(ConstManager.CONST_XML_PATH_DB1);
			
		} catch (Exception e) {
			em.ThrowException(e, "DB�����̐����Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	
}
