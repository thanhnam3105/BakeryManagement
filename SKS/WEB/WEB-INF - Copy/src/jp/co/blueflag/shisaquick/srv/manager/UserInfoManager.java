package jp.co.blueflag.shisaquick.srv.manager;

import javax.servlet.http.*;

import jp.co.blueflag.shisaquick.srv.base.ObjectBase;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * ���[�U�[�Z�b�V�����Ǘ�
 *  : ���[�U�[�Z�b�V�������̊Ǘ����s���B
 * @author TT.furuta
 * @since  2009/03/24
 */
public class UserInfoManager extends ObjectBase{

	//�����敪
	private String strShoriKbn = "";
	//���[�U�[ID
	//�����敪�F1�@JSP���O�C���y��JWS�ȊO�̏ꍇ
	//�����敪�F2�@JSP���O�C���̏ꍇ	
	//�����敪�F3�@JWS�̏ꍇ
	private String strUserId = "";
	
	/**
	 * ���[�U�[�Z�b�V�����Ǘ��R���X�g���N�^
	 */
	public UserInfoManager() {
		//���N���X�̃R���X�g���N�^
		super();
		
		//�C���X�^���X�ϐ��̏�����
		strShoriKbn = "";
		strUserId = "";
	
	}
	
	/**
	 * ���[�U�[���Q��
	 * @param  HttpServletRequest�@�F�@HttpRequest
	 * @param  requestData �F�@���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public UserInfoData userInfoManager(
			HttpServletRequest HttpRequest
			, RequestData requestData
			) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//���[�U�[���Ǘ�
		UserInfoData userInfo = null;
		//
		HttpSession session = null;
		
		try{
			//���N�G�X�g�f�[�^��菈���敪�A���[�U�[ID�̎擾
			getKaind(requestData);
			
			//�A�F�����敪���A�Z�b�V���������Ǘ�����B
			if (strShoriKbn.equals("1")){
				//�����敪�F1�@JSP���O�C���y��JWS�ȊO�̏ꍇ
				//	�Z�b�V�����`�F�b�N�N���X���ďo���B
				//	���[�U�[���Q�Ƃ��ďo���B

				//�Z�b�V�����`�F�b�N�N���X���ďo���B
				session = SessionCheck(HttpRequest);
				
				//���[�U�[����UserInfoDada
				userInfo = new UserInfoData(session);
				userInfo.CreatUserInfo(strShoriKbn, session.getAttribute("id_user").toString());
				
			}else if (strShoriKbn.equals("2")){
				//�����敪�F2�@JSP���O�C���̏ꍇ	
				//	�Z�b�V������񏉊����N���X���ďo���B
				//	���[�U�[���Q�Ƃ��ďo���B
				
				//�Z�b�V������񏉊����N���X���ďo���B
				session = SessionRemove(HttpRequest);
				
				if (!strUserId.equals("")){
					//���[�U�[����UserInfoDada�@
					userInfo = new UserInfoData(session);
					userInfo.CreatUserInfo(strShoriKbn, strUserId);
				}
				
			}else if (strShoriKbn.equals("3")){
				//�����敪�F3�@JWS�̏ꍇ
				//	���[�U�[����DB���Q��
				
				if (!strUserId.equals("")){
					//���[�U�[����UserInfoDada�@
					userInfo = new UserInfoData(null);
					userInfo.CreatUserInfo(strShoriKbn, strUserId);
				}
				
			}
			
			
			
			try{
				
				//JSP�N���������Z�b�V�����ɕۑ�����
				if (!toString(requestData.GetValue("USERINFO", 0, 0, "MOVEMENT_CONDITION")).equals("")){
					session.setAttribute("MOVEMENT_CONDITION"
							, toString(requestData.GetValue("USERINFO", 0, 0, "MOVEMENT_CONDITION")));
					
				}
				//JSP�N���������Z�b�V������胁���o�ɑޔ�����B
				userInfo.setMovement_condition(StringToList(
						toString(session.getAttribute("MOVEMENT_CONDITION")), ":::"));
				
			}catch(Exception e){
			
			}
			
			
		} catch (Exception e){
			em.ThrowException(e, "���[�U�[�Z�b�V�����Ǘ��Ɏ��s���܂����B");

		}
		return userInfo;
		
	}
	/**
	 * �Z�b�V�����̗L�����`�F�b�N����
	 * @param HttpRequest:�����������N�G�X�g
	 * @return�@HttpSession
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private HttpSession SessionCheck(HttpServletRequest HttpRequest) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		HttpSession ret = null;
		
		try{
			//�Z�b�V�������擾����
			ret = HttpRequest.getSession(false);

			//�Z�b�V������񂪎擾�o���Ă��邩����
			if (ret == null){
				//�擾�o���Ă��Ȃ��ꍇ

				//���Exception�𐶐�����
				em.ThrowException(ExceptionKind.���Exception, "E000101", "", "", "");

			} else {
				try {
					//�Z�b�V�����Ƀ��[�UID���������݂��Ȃ��ꍇ
					String strUserId = ret.getAttribute("id_user").toString();
					
				} catch (NullPointerException e) {
					//�Z�b�V�������^�C���A�E�g���Ă���
					//���Exception�𐶐�����
					em.ThrowException(ExceptionKind.���Exception, "E000101", "", "", "");
				}
			}

		}catch(Exception e){
			em.ThrowException(e, "���[�U�[�Z�b�V�����Ǘ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �Z�b�V������j�����ĐV���ȃZ�b�V�������J�n����B
	 * @param HttpRequest : �����������N�G�X�g
	 * @return HttpSession : �V���ɊJ�n���ꂽ�Z�b�V����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private HttpSession SessionRemove(HttpServletRequest HttpRequest) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		HttpSession ret = null;

		try{
			//�Z�b�V�����̔j��
			if (HttpRequest.getSession(false) != null){
				HttpRequest.getSession(false).invalidate();
				
			}
			//�V���ȃZ�b�V�������J�n
			ret = HttpRequest.getSession();
			
		}catch(Exception e){
			em.ThrowException(e, "Http�Z�b�V�����̊J�n�Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * ���N�G�X�g�f�[�^��菈���敪�A���[�U�[ID�̎擾
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void getKaind(RequestData requestData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//�@�F���N�G�X�g�f�[�^���A�@�\ID�uUSERINFO�v�̃f�[�^�𒊏o���A�����敪�@�E�@���[�U�[ID���擾����B
			for(int i=0; i<requestData.getItemList().size(); i++){

				//�@�\ID�̑ΏۃC���f�b�N�X�擾
				if (requestData.GetKindID(i).equals("USERINFO")){
					
					//���ڐ����[�v
					for (int j=0;j<requestData.GetValueCnt(i, 0);j++){

						if ("kbn_shori".equals(requestData.GetValueName(i,0,j))){
							//�����敪�i�[
							strShoriKbn = requestData.GetValue(i,0,0,j);

						} else if ("id_user".equals(requestData.GetValueName(i,0,j))){
							//���[�U�[ID�i�[
							strUserId = requestData.GetValue(i,0,0,j);

						}
					}
				}
			}
			
		}catch(Exception e){
			em.ThrowException(e, "���[�U�[�Z�b�V�����Ǘ��A�����敪�A���[�U�[ID�̎擾�Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	
}
