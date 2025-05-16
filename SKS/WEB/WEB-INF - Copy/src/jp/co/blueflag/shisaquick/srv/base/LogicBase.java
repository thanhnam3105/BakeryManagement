package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.base.BaseDao.DBCategory;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ���W�b�N�x�[�X
 *  : �Ɩ����W�b�N�̎������N���X
 * @author TT.furuta
 * @since  2009/03/25
 */
public class LogicBase extends ObjectBase{

	//���X�|���X�f�[�^
	protected RequestResponsKindBean responsData;
	//DB�̌��������{����
	protected SearchBaseDao searchDB;
	//DB�̍X�V�����{����
	protected UpdateBaseDao execDB;
	//���[�U�[���Ǘ�
	protected UserInfoData userInfoData = null;
	
	/**
	 * ���W�b�N�x�[�X�R���X�g���N�^ : �C���X�^���X����
	 */
	public LogicBase() {
		super();

	}
	
	/**
	 * ���W�b�N����
	 * �F�h����ŃI�[�o�[���C�h���āA�Ɩ����W�b�N�̎������L�q����
	 * @param reqKind : �@�\���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqKind
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���iLogicBase�j��ExecLogic�����s����܂���
		//���[�U�[���ޔ�
		this.userInfoData = _userInfoData;
		
		return responsData;
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
		
		createSearchDB(DBCategory.DB1);
		
	}
	/**
	 * DB�̌����̐���
	 * �FDB�̌����׽�𐶐�����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void createSearchDB(DBCategory DB_Category) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//DB�Z�b�V�����𐶐�
			searchDB = new SearchBaseDao(DB_Category);
			
		} catch (Exception e) {
			em.ThrowException(e, "DB�����̐����Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	/**
	 * DB�X�V�̐���
	 * �FDB�X�V�׽�𐶐�����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void createExecDB() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		createExecDB(DBCategory.DB1);

	}

	/**
	 * DB�X�V�̐���
	 * �FDB�X�V�׽�𐶐�����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	protected void createExecDB(DBCategory DB_Category) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		try {
			//DB�Z�b�V�����𐶐�
			execDB = new UpdateBaseDao(DB_Category);
			
		} catch (Exception e) {
			em.ThrowException(e, "DB�Ǘ��̐����Ɏ��s���܂����B");
			
		} finally {
			
		}

	}

}
