package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.PrintWriter;

import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;

import jp.co.blueflag.shisaquick.srv.base.ChangeXML;
import jp.co.blueflag.shisaquick.srv.base.CreateXML;
import jp.co.blueflag.shisaquick.srv.base.HttpServletBase;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.ResponsData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionBase;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.manager.DataCheckManager;
import jp.co.blueflag.shisaquick.srv.manager.InputCheckManager;
import jp.co.blueflag.shisaquick.srv.manager.LogicManager;
import jp.co.blueflag.shisaquick.srv.manager.RequestContoroler;
import jp.co.blueflag.shisaquick.srv.manager.UserInfoManager;


/**
 * 
 * AjaxServlet
 *  : XML�ʐM�A����A�Z�b�V�����Ǘ��A�C���v�b�g�`�F�b�N���Ǘ�����B
 * 
 * @author TT.furuta
 * @since  2009/03/23
 */
public class AjaxServlet extends HttpServletBase implements SingleThreadModel {

	//�ԋp�pXML�I�u�W�F�N�g
	private Document document = null;
	//���N�G�X�g�f�[�^�ێ��N���X
	private RequestData reqData = null;
	//���X�|���X�f�[�^�ێ��N���X
	private ResponsData respData = null;
	//���[�U�[���Ǘ�
	private UserInfoData userInfoData = null;
	
	/**
	 * HttpPost����M
	 *  : POST�ʐM�̑���M���s���B
	 * @param req : �T�[�u���b�g���N�G�X�g
	 * @param resp : �T�[�u���b�g���X�|���X
	 */
	public void doPost(HttpServletRequest _req, HttpServletResponse _res) {

		//�N���X�ϐ��̏������@
		reqData = null;
		respData = null;
		document = null;
		userInfoData = null;
		
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		ChangeXML changeXML = null;
		UserInfoManager userInfoManager = null;
		InputCheckManager inputCheckManager = null;
		RequestContoroler requestContoroler = null;
		
		try{
			
			//���N���X�iHttpServletBase�j�̏����������s��
			super.initServlet(_req, _res);

			//DI�ݒ�t�@�C���ǂݍ���
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_MANAGER);
			factory = new XmlBeanFactory(cls_rsr);

			//�C�T�[�u���b�g���N�G�X�g����ɁARequestData�𐶐�����
			//	XML��񒊏o�����ďo
			changeXML = (ChangeXML)factory.getBean("DI_ChangeXML");
			reqData = changeXML.createRequestData(req);

			//�D���N�G�X�g�Z�b�V�������p���A���[�U�[���Ǘ��ďo�������ďo���B
			//	���[�U�[���Ǘ��ďo�i�Z�b�V�����̊m�F�A���[�U�[���̎擾���s���j
			userInfoManager = (UserInfoManager)factory.getBean("DI_UserInfoManager");
			userInfoData = userInfoManager.userInfoManager(req, reqData);

			try{
				//�E�C���v�b�g�`�F�b�N�Ǘ����ďo���A�e�C���v�b�g�`�F�b�N���s���B
				//	�C���v�b�g�`�F�b�N�Ǘ��ďo
				inputCheckManager = (InputCheckManager)factory.getBean("DI_InputCheckManager");
				inputCheckManager.inputCheckmanager(reqData, userInfoData);

			} catch (ExceptionWaning e) {
				//�������Ȃ�
				e = null;
			}

			//�F�Ɩ����W�b�N����������
			//	���N�G�X�g�R���g���[���[�ďo
			requestContoroler = (RequestContoroler)factory.getBean("DI_RequestContoroler");
			respData = requestContoroler.mainContorol(reqData, userInfoData);

		} catch (Exception e){
			//�H��O�����i��O���������ʂɕϊ�����j
			respData = ExceptionProc(e);
			e = null;
						
		}finally{

			//�IXMLID���̏������ʂ�t������
			AddResult();

			//�JXMLID����USERINFO��t������
			AddUserInfo();

			//�G�����orespData���XML��޼ު�Ă𐶐����A���X�|���X�ʐM���s��
			ResponsProc(res, respData, factory);	
			
			//�����[�J���N���X�̔j��
			cls_rsr = null;
			factory = null;
			changeXML = null;
			userInfoManager = null;
			inputCheckManager = null;
			requestContoroler = null;

			//���C���X�^���X�N���X�̔j��
			//���N�G�X�g�f�[�^Bean�̊J��
			reqData.RemoveList();
			reqData = null;
			//���X�|���X�f�[�^Bean�̊J��
			respData.RemoveList();
			respData = null;
			//�G�N�Z�v�V�����}�l�[�W��
			em = null;
			//sml�h�L�������g
			document = null;
			//���[�U�[�C���t�H�f�[�^
			userInfoData.removeList();
			userInfoData = null;
			
		}
		//�K�x�[�W�̎��s
		//System.gc();
	
	}
	
	/**
	 * ڽ��ݽ����
	 *  : ڽ��ݽ�ێ��׽��XML�ϊ���ڽ��ݽ���M����
	 * @param ex : ��O�N���X
	 */
	private void ResponsProc(HttpServletResponse resp, ResponsData respData, XmlBeanFactory factory){
	
		PrintWriter out = null;
		
		//�����݃I�u�W�F�N�g����
		try {
			
			//�@�F�G���R�[�h���s���B�itext/xml; charset=UTF-8�j
			resp.setContentType(ConstManager.SRV_XML_RESP_ENCODE);
			
			out = resp.getWriter();
			
			//�A�F�����orespData���XML��޼ު�Ă𐶐����A���X�|���X�ʐM���s��
			CreateXML createXML = (CreateXML)factory.getBean("DI_CreateXML");
			
			//XML��񐶐������ďo
			document = createXML.writeXMLInfo(respData);
			
			//���O�@�R���\�[���Ƀ��X�|���XXML��\������
			if (!ConstManager.getConstValue(Category.�ݒ�, "CONST_DEBUG_LEVEL").equals("0")){

				TransformerFactory tfactory2 = TransformerFactory.newInstance(); 
		        Transformer transforme2 = tfactory2.newTransformer(); 
		        transforme2.setOutputProperty(OutputKeys.ENCODING, "Shift_JIS");
		        transforme2.transform(new DOMSource(document), new StreamResult(System.out)); 
				
			}
			
			//�B�FTransformerFactory���g�p���A��L�A�Ő����������X�|���XXML��ԋp
	        TransformerFactory tfactory = TransformerFactory.newInstance();
	        Transformer transformer = tfactory.newTransformer();
	        transformer.transform(new DOMSource(document), new StreamResult(out));

		} catch (Exception e) {
			//�C�FXML�������s�\�ȏꍇ�A��ŕԂ��B
			out.println("");
		} finally {
			out.close();
			out = null;
		}
	}

	/**
	 * ����߼��ڽ��ݽ�ێ��ϊ�
	 *  : �ُ���̒��o�E�]�@���s���B
	 * @param ex : ��O�N���X
	 */
	private ResponsData ExceptionProc(Exception ex){
		
		//���R�R�ŃL���b�`�����Exception�͉��L�Q��̂�
		//�P�D�C���v�b�g�`�F�b�N/�Z�b�V�����`�F�b�N�Ŗ����I�ɔ����������G���[
		//�Q�DAjaxServlet�Ŕ��������A�\�����ʏ�Q

		ExceptionBase ExBase = null;

		try{
			//Exception�̎�ނ𔻒�
			if ( ex.getClass().equals(ExceptionSystem.class) ) {
				ExBase = (ExceptionBase) ex;
	
			} else if ( ex.getClass().equals(ExceptionUser.class) ) {
				ExBase = (ExceptionBase) ex;
	
			} else if ( ex.getClass().equals(ExceptionWaning.class) ) {
				ExBase = (ExceptionBase) ex;
	
			} else {
				//�Q�DAjaxServlet�Ŕ��������A�\�����ʏ�Q���AExceptionSystem�ɕϊ�����
				ExBase = em.cnvException(ex, "AjaxServlet�ŁA�V�X�e����O���������܂����B");
				
			}
			
			//respons�̐���
			//Exception�N���X�̓��e���AResponce�f�[�^�ɒu��������
			
			//XMLID���擾
			String XmlID = "";
			if (null == reqData){
				XmlID = "Non XmlID";
	
			}else{
				XmlID = reqData.getID();
			}
			//���X�|���X�����i�@�\ID�w�j
			RequestResponsKindBean respBean = new RequestResponsKindBean();
			respBean.setID("AjaxServlet");
			//������
			respBean.addFieldVale(0, 0, "flg_return", "false");
			//���b�Z�[�W 
			respBean.addFieldVale(0, 0, "msg_error", ExBase.getUserMsg());
			//�������� 
			respBean.addFieldVale(0, 0, "nm_class", ExBase.getClassName());
			//���b�Z�[�W�ԍ� 
			respBean.addFieldVale(0, 0, "no_errmsg", ExBase.getMsgNumber());
			//�G���[�R�[�h 
			respBean.addFieldVale(0, 0, "cd_error", ExBase.getSystemErrorCD());
			//�V�X�e�����b�Z�[�W 
			respBean.addFieldVale(0, 0, "msg_system", ExBase.getSystemErrorMsg());
			//�f�o�b�N���[�h
			respBean.addFieldVale(0, 0, "debuglevel", 
					ConstManager.getConstValue(ConstManager.Category.�ݒ�, "CONST_DEBUG_LEVEL"));
	
			//���X�|���X�̐���
			if (null == respData){
				respData = new ResponsData(null);
				respData.setID(XmlID);
			}
			respData.AddItem(respBean);

		} catch (Exception exc){
			//���s���ԋp���ʂ�������
			respData = null;
		}
		//�G�N�Z�v�V�����N���X�̔j��
		ExBase = null;

		return respData;
		
	}
	/**
	 * �IXMLID���̏������ʂ�t������
	 */
	private void AddResult(){

		String strMsg = "";
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
		String msg = "";
		String name;
//ADD 2013/06/28 ogawa�yQP@30151�zNo.38 end
		RequestResponsKindBean ResultBean = null;

		try{
			
			//1)�������ʂ𔻒肷��
			RequestResponsKindBean respNG = ResultCheck(); 

			//3)����Bean�Ɋi�[����
			
			//�@�\ID
			ResultBean = new RequestResponsKindBean(); 
			ResultBean.setID("RESULT");

			if (respNG == null){
				//�����̏ꍇ

				//��������
				ResultBean.addFieldVale(0, 0, "flg_return", "true");
				//���b�Z�[�W 
				try{
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 Start
//�C���O�\�[�X
//					strMsg = ConstManager.getConstValue(Category.���b�Z�[�W, "S"+respData.getID());
//�C����\�[�X
					for (int i=0;i < respData.GetKindCnt();i++){
						
						//�@�FrespData��背�X�|���X�iResponsData�j�𒊏o
						RequestResponsKindBean respKinoData = (RequestResponsKindBean)respData.getItemList().get(i);
						
						for (int j=0;j < respKinoData.GetCnt();j++){
							RequestResponsTableBean respTblData = respKinoData.getTableItem(j);
							for (int k=0;k < respTblData.GetCnt();k++){
								RequestResponsRowBean rowdata = respTblData.getRowItem(k);
								for( int l=0; l < rowdata.GetCnt(); l++){
									
									name = rowdata.getFieldItem(l).getName();
									if(name.equals("msg_cd")){
										msg = rowdata.getFieldItem(l).getValue();
										if((msg != null) && (msg.length() != 0)){
											strMsg = ConstManager.getConstValue(Category.���b�Z�[�W, "S"+msg);
											break;
										}
									}
								}
							}
						}
						
					}

					if((msg == null) || (msg.length() == 0)){
						strMsg = ConstManager.getConstValue(Category.���b�Z�[�W, "S"+respData.getID());
					}
//MOD 2013/06/28 ogawa�yQP@30151�zNo.38 end
				}catch(ExceptionWaning e){

				}
				ResultBean.addFieldVale(0, 0, "msg_error", strMsg);
				//�������� 
				ResultBean.addFieldVale(0, 0, "nm_class", "");
				//���b�Z�[�W�ԍ� 
				ResultBean.addFieldVale(0, 0, "no_errmsg", "");
				//�G���[�R�[�h 
				ResultBean.addFieldVale(0, 0, "cd_error", "");
				//�V�X�e�����b�Z�[�W 
				ResultBean.addFieldVale(0, 0, "msg_system", "");
				//�f�o�b�N���[�h
				ResultBean.addFieldVale(0, 0, "debuglevel",
						ConstManager.getConstValue(Category.�ݒ�, "CONST_DEBUG_LEVEL"));
				
			}else{
				//���s�̏ꍇ

				//��������
				ResultBean.addFieldVale(0, 0, "flg_return", "false");
				//���b�Z�[�W 
				try{
					strMsg = ConstManager.getConstValue(Category.���b�Z�[�W, "E"+respData.getID())
									+ "\n"
									+ respNG.getFieldVale(0, 0, "msg_error");
				}catch(ExceptionWaning e){

				}
				ResultBean.addFieldVale(0, 0, "msg_error", strMsg);
				//�������� 
				ResultBean.addFieldVale(0, 0, "nm_class",  
						respNG.getFieldVale(0, 0, "nm_class"));
				//���b�Z�[�W�ԍ� 
				ResultBean.addFieldVale(0, 0, "no_errmsg",  
						respNG.getFieldVale(0, 0, "no_errmsg"));
				//�G���[�R�[�h 
				ResultBean.addFieldVale(0, 0, "cd_error",  
						respNG.getFieldVale(0, 0, "cd_error"));
				//�V�X�e�����b�Z�[�W 
				ResultBean.addFieldVale(0, 0, "msg_system",  
						respNG.getFieldVale(0, 0, "msg_system"));
				//�f�o�b�N���[�h
				ResultBean.addFieldVale(0, 0, "debuglevel",  
						ConstManager.getConstValue(Category.�ݒ�, "CONST_DEBUG_LEVEL"));
			}
			//4)����Bean(ResponsData)�ɒǉ�����
			respData.AddKind(ResultBean,0);
			
		}catch(Exception e){

		}finally{
			ResultBean = null;
		}
	}

	/**
	 * �I 1)�������ʂ𔻒肷��
	 */
	private RequestResponsKindBean ResultCheck(){

		RequestResponsKindBean ret = null;

		try{
			//�@�\ID���̏������ʂ𔻒肵�A���s�ifalse�j���������Bean��ԋp����B
	        for (int ix=0;ix < respData.GetCnt();ix++){
	        	//�s�����̋@�\ID��T���o��
	        	if( respData.GetValue(ix, 0, 0, "flg_return")=="false"){
	        		ret =  (RequestResponsKindBean) respData.GetItem(ix);
	        	}
	        }

		}catch(Exception e){

		}finally{

		}
		return ret;
	}
	
	/**
	 * USERINFOXML�I�u�W�F�N�g����
	 * @param document : XML�h�L�������g
	 * @param elmXML : XMLID�G�������g
	 * @return ���[�U�[��{���̃G�������g
	 */
	private void AddUserInfo(){
		
		RequestResponsKindBean UserInfoBean = null;
		
		try{
			//1)����Bean�Ɋi�[����

			//�@�\ID
			UserInfoBean = new RequestResponsKindBean(); 
			UserInfoBean.setID("USERINFO");

			//�@�F���[�U�[��{���f�[�^�ێ��N���X���A�f�[�^���擾���AXML�h�L�������g�ɐݒ肷��B
	        if (null == userInfoData.getId_data()){

        		//�����i���ID,�@�\ID,�f�[�^ID�j�������ꍇ
        		
	        	//�������ʇ@
	        	UserInfoBean.addFieldVale(0, 0, "flg_return", "ture");
	        	//�������ʇA
	        	UserInfoBean.addFieldVale(0, 0, "msg_error", "");
	        	//�������ʇB
	        	UserInfoBean.addFieldVale(0, 0, "nm_class", "");
	        	//�������ʇC
	        	UserInfoBean.addFieldVale(0, 0, "no_errmsg", "");
	        	//�������ʇD
	        	UserInfoBean.addFieldVale(0, 0, "cd_error", "");
	        	//�������ʇE
	        	UserInfoBean.addFieldVale(0, 0, "msg_system", "");
	        	//�V�X�e���o�[�W�����ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "systemversion", "");
	        	//���[�U�[ID�ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "id_user", userInfoData.getId_user());
	        	//���[�U�[���̐ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "nm_user", userInfoData.getNm_user());
	        	//��ЃR�[�h�ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "cd_kaisha", userInfoData.getCd_kaisha());
	        	//��Ж��ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "nm_kaisha", userInfoData.getNm_kaisha());
	        	//�����R�[�h�ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "cd_busho", userInfoData.getCd_busho());
	        	//�������ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "nm_busho", userInfoData.getNm_busho());
	        	//�O���[�vCD�ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "cd_group", userInfoData.getCd_group());
	        	//�O���[�v���̐ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "nm_group", userInfoData.getNm_group());
	        	//�`�[��CD�ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "cd_team", userInfoData.getCd_team());
	        	//�`�[�����̐ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "nm_team", userInfoData.getNm_team());
	        	//���e����CD�ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "cd_literal", userInfoData.getCd_literal());
	        	//���e�������̐ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "nm_literal", userInfoData.getNm_literal());
	        	//���ID�ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "id_gamen", "");
	        	//�@�\ID�ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "id_kino", "");
	        	//�f�[�^ID�ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "id_data", "");
	        	//�f�o�b�O���x���ݒ�
	        	UserInfoBean.addFieldVale(0, 0, "debuglevel",
	        			ConstManager.getConstValue(Category.�ݒ�, "CONST_DEBUG_LEVEL"));
	        	
	        } else {

	        	for (int i=0;i<userInfoData.getId_data().size();i++){

	        		//�����i���ID,�@�\ID,�f�[�^ID�j���L��ꍇ
	        		
		        	//�������ʇ@
		        	UserInfoBean.addFieldVale(0, i, "flg_return", "ture");
		        	//�������ʇA
		        	UserInfoBean.addFieldVale(0, i, "msg_error", "");
		        	//�������ʇB
		        	UserInfoBean.addFieldVale(0, i, "nm_class", "");
		        	//�������ʇC
		        	UserInfoBean.addFieldVale(0, i, "no_errmsg", "");
		        	//�������ʇD
		        	UserInfoBean.addFieldVale(0, i, "cd_error", "");
		        	//�������ʇE
		        	UserInfoBean.addFieldVale(0, i, "msg_system", "");
		        	//�V�X�e���o�[�W�����ݒ�
		        	UserInfoBean.addFieldVale(0, i, "systemversion", userInfoData.getSystemversion());
		        	//���[�U�[ID�ݒ�
		        	UserInfoBean.addFieldVale(0, i, "id_user", userInfoData.getId_user());
		        	//���[�U�[���̐ݒ�
		        	UserInfoBean.addFieldVale(0, i, "nm_user", userInfoData.getNm_user());
		        	//��ЃR�[�h�ݒ�
		        	UserInfoBean.addFieldVale(0, i, "cd_kaisha", userInfoData.getCd_kaisha());
		        	//��Ж��ݒ�
		        	UserInfoBean.addFieldVale(0, i, "nm_kaisha", userInfoData.getNm_kaisha());
		        	//�����R�[�h�ݒ�
		        	UserInfoBean.addFieldVale(0, i, "cd_busho", userInfoData.getCd_busho());
		        	//�������ݒ�
		        	UserInfoBean.addFieldVale(0, i, "nm_busho", userInfoData.getNm_busho());
		        	//�O���[�vCD�ݒ�
		        	UserInfoBean.addFieldVale(0, i, "cd_group", userInfoData.getCd_group());
		        	//�O���[�v���̐ݒ�
		        	UserInfoBean.addFieldVale(0, i, "nm_group", userInfoData.getNm_group());
		        	//�`�[��CD�ݒ�
		        	UserInfoBean.addFieldVale(0, i, "cd_team", userInfoData.getCd_team());
		        	//�`�[�����̐ݒ�
		        	UserInfoBean.addFieldVale(0, i, "nm_team", userInfoData.getNm_team());
		        	//���e����CD�ݒ�
		        	UserInfoBean.addFieldVale(0, i, "cd_literal", userInfoData.getCd_literal());
		        	//���e�������̐ݒ�
		        	UserInfoBean.addFieldVale(0, i, "nm_literal", userInfoData.getNm_literal());
		        	//���ID�ݒ�
		        	UserInfoBean.addFieldVale(0, i, "id_gamen", userInfoData.getId_gamen().get(i).toString());
		        	//�@�\ID�ݒ�
		        	UserInfoBean.addFieldVale(0, i, "id_kino", userInfoData.getId_kino().get(i).toString());
		        	//�f�[�^ID�ݒ�
		        	UserInfoBean.addFieldVale(0, i, "id_data", userInfoData.getId_data().get(i).toString());
		        	//�f�o�b�O���x���ݒ�
		        	UserInfoBean.addFieldVale(0, i, "debuglevel", 
		        			ConstManager.getConstValue(Category.�ݒ�, "CONST_DEBUG_LEVEL"));
		    
		        }
	        	
	        }
	        //���X�|���X�f�[�^�ɒǉ�
	        respData.AddKind(UserInfoBean,1);
        
		}catch(Exception e){

		}finally{
			UserInfoBean = null;
		}
	}

}
