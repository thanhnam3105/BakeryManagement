package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN0020_Logic;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * �������Z�����\������DB����
 *  : �������Z��ʂ̌��������̏����擾����B
 *  �@�\ID�FFGEN0012_Logic
 *  
 * @author Nishigawa
 * @since  2009/10/23
 */
public class FGEN0012_Logic extends LogicBase{
	
	//����NO
	BigDecimal cd_shisaku_syainID = new BigDecimal(-1);
	int cd_shisaku_nen = -1;
	int cd_shisaku_oi = -1;
	int cd_shisaku_eda = -1; //�yQP@00342�z
	
	//������
	List<?> listShisaku = null;	
	//����ُ��
	List<?> listSanpuru = null;	
	//�������
	List<?> listGenryo = null;	
	//���ޏ��
	List<?> listShizai = null;
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	//��{���T�u
	List<?> listKihonSub = null;
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
	
	/**
	 * �������Z�����\������DB����
	 * : �C���X�^���X����
	 */
	public FGEN0012_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * �������Z�����\��
	 *  : �������Z��ʂ̌��������̏����擾����B
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
		
		//�Čv�Z���N�G�X�g
		RequestResponsKindBean reqDataKeisan = null;
		//�Čv�Z���X�|���X
		RequestResponsKindBean resDataKeisan = null;
		//�������Z�����\�����X�|���X
		RequestResponsKindBean ret = null;
		//�Čv�Z�N���X
		CGEN0020_Logic clsCGEN0020_Logic = null;

		
		try {
			
			cd_shisaku_syainID = toDecimal(reqData.getFieldVale("kihon", "rec", "cd_shain"), "-1");
			cd_shisaku_nen = toInteger(reqData.getFieldVale("kihon", "rec", "nen"), -1);
			cd_shisaku_oi = toInteger(reqData.getFieldVale("kihon", "rec", "no_oi"), -1);

			//�yQP@00342�z
			cd_shisaku_eda = toInteger(reqData.getFieldVale("kihon", "rec", "no_eda"), -1);
			
			//DB���擾
			getDBKomoku();
			
			//���N�G�X�g�C���X�^���X
			reqDataKeisan = new RequestResponsKindBean();
			//�Čv�Z���N�G�X�g����
			makeReqDataKeisan(reqDataKeisan);
			
			//�Čv�Z
			clsCGEN0020_Logic = new CGEN0020_Logic();
			resDataKeisan = clsCGEN0020_Logic.ExecLogic(reqDataKeisan, userInfoData);
			
			//���X�|���X�̃C���X�^���X
			ret = new RequestResponsKindBean();
			//�������Z�����\�����X�|���X����
			makeResData(ret, resDataKeisan);
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔j��
			removeList(listShisaku);	
			removeList(listSanpuru);	
			removeList(listGenryo);	
			removeList(listShizai);	
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			removeList(listKihonSub);
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

		}
		return ret;
		
	}
	/**
	 * �Čv�Z�N���X�ɓn�����N�G�X�g�𐶐�
	 * @param reqDataKeisan	�F�����ナ�N�G�X�g
	 * @param listShisaku	�FDB������
	 * @param listSanpuru	�FDB�T���v�����
	 * @param listGenryo	�FDB�������
	 * @param listShizai	�FDB���ޏ��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan(

			 RequestResponsKindBean reqDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		
		try{
			

			//ID�ݒ�
			reqDataKeisan.setID("CGEN0020");
			
			//��{���N�G�X�g�̐���
			makeReqDataKeisan_Kihon(reqDataKeisan);
			//�������N�G�X�g�̐���
			makeReqDataKeisan_Genryo(reqDataKeisan);
			//�v�Z���N�G�X�g�̐���
			makeReqDataKeisan_Keisan(reqDataKeisan);
			//���ރ��N�G�X�g�̐���
			makeReqDataKeisan_Shizai(reqDataKeisan);
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			//��{���T�u���N�G�X�g�̐���
			makeReqDataKihon_Sub(reqDataKeisan);
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B ");
			
		}finally{
			
		}
		
	}
	/**
	 * ���X�|���X�f�[�^�̐���
	 * @param resData		�F�ҏW���ʁ@���X�|���X�f�[�^
	 * @param resDataKeisan	�F�v�Z�N���X�̌���
	 * @param listShisaku	�FDB���@������
	 * @param listSanpuru	�FDB���@�T���v�����
	 * @param listGenryo	�FDB���@�������
	 * @param listShizai	�FDB���@���ޏ��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeResData(

			 RequestResponsKindBean resData
			,RequestResponsKindBean resDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		
		try{

			
			resData.setID("FGEN0012");
			
			//��{�iKihon�j���X�|���X����
			resData.addTableItem(resDataKeisan.getTableItem("kihon"));
			
			//�����igenryo�j���X�|���X����
			makeResData_genryo(resData, resDataKeisan);
			
			//����ishisaku�{��seq�j���X�|���X����
			for(int i = 0 ; i < toInteger(resData.getFieldVale("kihon", "rec", "cnt_sanpuru")); i++){
				
				resData.addTableItem(
						resDataKeisan.getTableItem("shisaku" + toString(i)));
				
			}
			
			//�v�Z�ikeisan�j���X�|���X����
			resData.addTableItem(resDataKeisan.getTableItem("keisan"));
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@���X�|���X�����Ɏ��s���܂����B ");
			
		}finally{
			
		}
		
	}
	/**
	 * ������񃌃X�I���X����
	 * @param resData		�F�ҏW���ʁ@���X�|���X�f�[�^
	 * @param resDataKeisan	�F�v�Z�N���X�̌���
	 * @param listGenryo	�FDB���@�������
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeResData_genryo(

			 RequestResponsKindBean resData
			,RequestResponsKindBean resDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int cntRow = 0;
		int cntKote = 0;
		int intCdKote = -1;
		String strHenkou = "";
		String strTemp1 = "";
		String strTemp2 = "";
		
		try{

			for (int i = 0 ; i < listGenryo.size(); i++){

				Object[] items = (Object[]) listGenryo.get(i);
				
				//�H���s�̐ݒ�
				if (intCdKote == toInteger(items[3])){
					
				}else{

					cntRow++;
					cntKote++;
					intCdKote = toInteger(items[3]);
					
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", toString(items[3]));
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", toString(items[18]));
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", toString(items[17]));
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
					
//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/2/16 START -----------------------------------												
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_kojo_tanka", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kaisha_tanka", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kojo_tanka", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_kojo_budomari", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kaisha_budomari", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kojo_budomari", "");
//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/2/16 END -------------------------------------												
					
				}

				cntRow++;

				// ----------------------------------------------------------------------+
				// �������ʇ@	flg_return
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "flg_return", "true");
				// ----------------------------------------------------------------------+
				// �������ʇA	msg_error
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "msg_error", "");
				// ----------------------------------------------------------------------+
				// �������ʇB	nm_class
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "nm_class", "");
				// ----------------------------------------------------------------------+
				// �������ʇE	no_errmsg
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "no_errmsg", "");
				// ----------------------------------------------------------------------+
				// �������ʇC	cd_error
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_error", "");
				// ----------------------------------------------------------------------+
				// �������ʇD	msg_system
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "msg_system", "");
				// ----------------------------------------------------------------------+
				// �H����		sort_kotei
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "sort_kotei", toString(items[13]));
				// ----------------------------------------------------------------------+
				// �H��CD		cd_kotei
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kotei", toString(items[3]));
				// ----------------------------------------------------------------------+
				// �H��SEQ		seq_kotei
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "seq_kotei", toString(items[4]));
				// ----------------------------------------------------------------------+
				// ����CD		cd_genryo
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_genryo", toString(items[10]));
				// ----------------------------------------------------------------------+
				// ������		nm_genryo
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "nm_genryo", toString(items[5]));
				// ----------------------------------------------------------------------+
				// �ύX�A��		henko_renraku
				// ----------------------------------------------------------------------+
//				if (toDouble(items[11], -1) == toDouble(items[15], -1)
//					&&	toDouble(items[12], -1) == toDouble(items[16], -1)){
//					strHenkou = "";
//
//				}else{
//					strHenkou = "��";
//					
//				}

//				//����CD�̐擪1�������擾
//				strTemp1 = toString(items[10]);
//				if(!strTemp1.equals("")){
//					strTemp1 = strTemp1.substring(0, 1);
//					
//				}
//				//�������̐擪1�������擾
//				strTemp2 = toString(items[5]);
//				if(!strTemp1.equals("")){
//					strTemp1 = strTemp1.substring(0, 1);
//					
//				}
//				if (strTemp1.equals("N") || strTemp2.equals("��")){
//					//����CD�̐擪���hN�h�i�V�K�����j�@���́A�������̐擪���h���h�i3�����Ԗ��g�p�����j�̏ꍇ
//
//					//����������̒l�Ɣ�r����B
//					if (toDouble(items[11], -1) != toDouble(items[15], -1)
//						||	toDouble(items[12], -1) != toDouble(items[16], -1)){
//						strHenkou = "��";
//
//					}else{
//						strHenkou = "";
//
//					}
//					
//				}else{
//					//�V�K�����A3�����Ԗ��g�p�����A�ȊO�̏ꍇ
//					
//					//�}�X�^�̒l�Ɣ�r����B
//					if (toDouble(items[11], -1) != toDouble(items[8], -1)
//						||	toDouble(items[12], -1) != toDouble(items[9], -1)){
//						strHenkou = "��";
//
//					}else{
//						strHenkou = "";
//
//					}
//					
//				}
				
				//�}�X�^�̒l�Ɣ�r����B
				if (toDouble(items[11], -1) != toDouble(items[8], -1)
					||	toDouble(items[12], -1) != toDouble(items[9], -1)){
					strHenkou = "��";

				}else{
					strHenkou = "";

				}
				
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "henko_renraku", strHenkou);
				
//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.32
				// ----------------------------------------------------------------------+
				// �P��			tanka
				// ----------------------------------------------------------------------+
//				if(toDouble(items[11], -1) > -1){
//					resData.addFieldVale(
//							"genryo", "rec" + toString(cntRow)
//							, "tanka"
//							, toString(
//									toDouble(items[11], -1)
//									, 2
//									, 2
//									, true
//									, "")
//							);
//					
//				}else{
//					resData.addFieldVale("genryo", "rec" + toString(cntRow)
//							, "tanka", "");
//					
//				}
				if(toDouble(items[11], -1) > -1){
					resData.addFieldVale(
							"genryo", "rec" + toString(cntRow)
							, "tanka"
							, toString(
									toDouble(items[11], -1)
									, 2
									, 2
									, true
									, "0.00")
							);
					
				}else{
					resData.addFieldVale("genryo", "rec" + toString(cntRow)
							, "tanka", "0.00");
					
				}
				// ----------------------------------------------------------------------+
				// ����			budomari
				// ----------------------------------------------------------------------+
//				if(toDouble(items[12], -1) > -1){
//					resData.addFieldVale(
//							"genryo", "rec" + toString(cntRow)
//							, "budomari"
//							, toString(
//									toDouble(items[12], -1)
//									, 2
//									, 2
//									, true
//									, "")
//							);
//					
//				}else{
//					resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari"
//							, "");
//					
//				}
				if(toDouble(items[12], -1) > -1){
					resData.addFieldVale(
							"genryo", "rec" + toString(cntRow)
							, "budomari"
							, toString(
									toDouble(items[12], -1)
									, 2
									, 2
									, true
									, "0.00")
							);
					
				}else{
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari"
							, "0.00");
					
				}
//mod end --------------------------------------------------------------------------------
				
				// ----------------------------------------------------------------------+
				// �����s�t���O	genryo_fg
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "genryo_fg", "1");
				
				
//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/2/16 START -----------------------------------							
				// ----------------------------------------------------------------------+
				// �P���@�H�ꖼ		                nm_kojo_tanka
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "nm_kojo_tanka", toString(items[19]));
				// ----------------------------------------------------------------------+
				// �P���@��ЃR�[�h�i�}�X�^�j		cd_kaisha_tanka
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kaisha_tanka", toString(items[20]));
				// ----------------------------------------------------------------------+
				// �P���@�H��R�[�h�i�}�X�^�j		cd_kojo_tanka
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kojo_tanka", toString(items[21]));
				// ----------------------------------------------------------------------+
				// ������@�H�ꖼ		                nm_kojo_budomari
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "nm_kojo_budomari", toString(items[22]));
				// ----------------------------------------------------------------------+
				// ������@��ЃR�[�h�i�}�X�^�j		cd_kaisha_budomari
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kaisha_budomari", toString(items[23]));
				// ----------------------------------------------------------------------+
				// ������@�H��R�[�h�i�}�X�^�j		cd_kojo_budomari
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kojo_budomari", toString(items[24]));
//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/2/16 END -------------------------------------
				
				
			}
			
			//�H���d�ʍ��v�s�̐ݒ�
			for (int i = 0 ; i < cntKote; i++){

				cntRow++;
				
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", toString(i + 1) + "�H��(Kg)");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
				
			}
			
			//���v�d�ʍs�̐ݒ�

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "���v�d��(Kg)");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			
			//���v�d��d�ʂ̐ݒ�

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "���v�d��d��(Kg)");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//���_�̐ݒ�

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "���_(��)");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//�H���̐ݒ�

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "�H��(��)");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//�������_�x�̐ݒ�

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "�������_�x");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//�������H���̐ݒ�

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "�������H��");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//�������|�_�̐ݒ�

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "�������|�_");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@���X�|���X�����Ɏ��s���܂����B ");
			
		}finally{
			
		}
		
	}
	/**
	 * DB���擾
	 * @param listShisaku	�F �������ʁ@������
	 * @param listSanpuru	�F �������ʁ@�T���v�����
	 * @param listGenryo	�F �������ʁ@�������
	 * @param listShizai	�F �������ʁ@���ޏ��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void getDBKomoku() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//SQL�o�b�t�@
		StringBuffer strSQL = new StringBuffer();
		
		try{

			//DB�R�l�N�V����
			createSearchDB();
			
			//�����񌟍�

			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  T310.cd_shain ");		//0
			strSQL.append(" ,T310.nen ");			//1
			strSQL.append(" ,T310.no_oi ");			//2
			strSQL.append(" ,T310.su_iri ");		//3
			strSQL.append(" ,T310.genka ");			//4
			strSQL.append(" ,T310.baika ");			//5
			strSQL.append(" ,T310.cd_genka_tani ");	//6
			strSQL.append(" ,T310.fg_keisan ");		//7
			
			//�yQP@00342�z
			strSQL.append(" ,T310.no_eda ");			//8
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shisakuhin AS T310 ");
			strSQL.append(" LEFT JOIN tr_shisakuhin AS T110 ");
			strSQL.append(" ON  T310.cd_shain = T110.cd_shain ");
			strSQL.append(" AND T310.nen      = T110.nen ");
			strSQL.append(" AND T310.no_oi    = T110.no_oi ");
			strSQL.append(" WHERE ");
			strSQL.append("     T310.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T310.nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T310.no_oi    = " + toString(cd_shisaku_oi) + " ");
			//�yQP@00342�z
			strSQL.append(" AND T310.no_eda    = " + toString(cd_shisaku_eda) + " ");
			//DB����
			listShisaku = this.searchDB.dbSearch(strSQL.toString());
			
			//����ُ�񌟍�
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  T131.cd_shain ");			//0
			strSQL.append(" ,T131.nen ");				//1
			strSQL.append(" ,T131.no_oi ");				//2
			strSQL.append(" ,T131.seq_shisaku ");		//3
			strSQL.append(" ,T131.juryo_shiagari_g ");	//4
			strSQL.append(" ,T141.zyusui ");			//5
			strSQL.append(" ,T141.zyuabura ");			//6
			strSQL.append(" ,T141.hiju ");				//7
			
			//�ۑ�Ǘ��䒠�@No116�@�F�@������t���o�^���ɕύX�@TT.Nishigawa START -----------------------------------
			strSQL.append(" ,CONVERT(VARCHAR,T331.dt_toroku,111) ");	//8
			//�ۑ�Ǘ��䒠�@No116�@�F�@������t���o�^���ɕύX�@TT.Nishigawa END    -----------------------------------
			
			strSQL.append(" ,T131.nm_sample ");			//9
			strSQL.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) ");	//10
			strSQL.append(" ,T141.gokei ");				//11
			strSQL.append(" ,T331.budomari ");			//12
			strSQL.append(" ,T331.heikinjuten ");		//13
			strSQL.append(" ,T331.cs_kotei ");			//14
			strSQL.append(" ,T331.kg_kotei ");			//15
			// ADD 2013/11/1 QP@30154 okano start
			strSQL.append(" ,T331.cs_rieki ");			//16
			strSQL.append(" ,T331.kg_rieki ");			//17
			// ADD 2013/11/1 QP@30154 okano end
			
//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38			
			strSQL.append(" ,T133.no_chui ");			//18
			strSQL.append(" ,T133.chuijiko ");			//19
//mod end --------------------------------------------------------------------------------
			
			//�yQP@00342�z
			strSQL.append(" ,T331.fg_chusi ");			//20
//ADD 2013/07/09 ogawa �yQP@30151�zNo.13 start
			strSQL.append(" ,T331.fg_koumokuchk ");		//21 ���ڌŒ�`�F�b�N
//ADD 2013/07/09 ogawa �yQP@30151�zNo.13 end
			
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start	
			strSQL.append(" ,T332.zyusui ");			//22 �Œ荀�ڕ\���p�F�[�U�ʐ����i���j
			strSQL.append(" ,T332.zyuabura ");			//23 �Œ荀�ڕ\���p�F�[�U�ʖ����i���j
			strSQL.append(" ,T332.gokei ");				//24 �Œ荀�ڕ\���p�F���v�ʁi���j 
			strSQL.append(" ,T332.hiju ");				//25 �Œ荀�ڕ\���p�F��d
			strSQL.append(" ,T332.reberu ");			//26 �Œ荀�ڕ\���p�F���x���ʁi�s�j
			strSQL.append(" ,T332.hijukasan ");			//27 �Œ荀�ڕ\���p�F��d���Z�ʁi�s�j 
			strSQL.append(" ,T332.cs_genryo ");			//28 �Œ荀�ڕ\���p�F������i�~�j/�P�[�X
			strSQL.append(" ,T332.cs_zairyohi ");		//29 �Œ荀�ڕ\���p�F�ޗ���i�~�j/�P�[�X
			strSQL.append(" ,T332.cs_genka ");			//30 �Œ荀�ڕ\���p�F�����v�i�~�j/�P�[�X
			strSQL.append(" ,T332.ko_genka ");			//31 �Œ荀�ڕ\���p�F�����v�i�~�j/��
			strSQL.append(" ,T332.kg_genryo ");			//32 �Œ荀�ڕ\���p�F������i�~�j/�s 
			strSQL.append(" ,T332.kg_zairyohi ");		//33 �Œ荀�ڕ\���p�F�ޗ���i�~�j/�s 
			strSQL.append(" ,T332.kg_genka ");			//34 �Œ荀�ڕ\���p�F�����v�i�~�j/�s
			strSQL.append(" ,T332.baika ");				//35 �Œ荀�ڕ\���p�F���� 
			strSQL.append(" ,T332.arari ");				//36 �Œ荀�ڕ\���p�F�e���i���j 
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisaku AS T131 ");
			strSQL.append(" LEFT JOIN tr_shisan_shisaku AS T331 ");
			strSQL.append(" ON  T331.cd_shain    = T131.cd_shain ");
			strSQL.append(" AND T331.nen         = T131.nen ");
			strSQL.append(" AND T331.no_oi       = T131.no_oi ");
			strSQL.append(" AND T331.seq_shisaku = T131.seq_shisaku ");
			strSQL.append(" LEFT JOIN tr_genryo AS T141 ");
			strSQL.append(" ON  T131.cd_shain    = T141.cd_shain ");
			strSQL.append(" AND T131.nen         = T141.nen ");
			strSQL.append(" AND T131.no_oi       = T141.no_oi ");
			strSQL.append(" AND T131.seq_shisaku = T141.seq_shisaku ");
			
//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38			
			strSQL.append(" LEFT JOIN dbo.tr_cyuui AS T133 ");
			strSQL.append(" ON T131.cd_shain = T133.cd_shain ");	
			strSQL.append(" AND T131.nen = T133.nen ");	
			strSQL.append(" AND T131.no_oi = T133.no_oi ");	
			strSQL.append(" AND T131.no_chui = T133.no_chui ");	
//mod end --------------------------------------------------------------------------------
			
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start				
			strSQL.append(" LEFT JOIN tr_shisan_shisaku_kotei AS T332 ");
			strSQL.append(" ON T332.cd_shain = T331.cd_shain ");
			strSQL.append(" AND T332.nen = T331.nen ");
			strSQL.append(" AND T332.no_oi = T331.no_oi ");
			strSQL.append(" AND T332.seq_shisaku = T331.seq_shisaku ");
			strSQL.append(" AND T332.no_eda = T331.no_eda ");
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end			
			
			//�yQP@00342�z
			strSQL.append(" WHERE ");
			strSQL.append("     T331.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T331.nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T331.no_oi    = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T331.no_eda    = " + toString(cd_shisaku_eda) + " ");
			strSQL.append(" AND T131.flg_shisanIrai    = 1 ");
			strSQL.append(" ORDER BY ");
			strSQL.append("  T131.cd_shain ");
			strSQL.append(" ,T131.nen ");
			strSQL.append(" ,T131.no_oi ");
			strSQL.append(" ,T131.sort_shisaku ");
			
			//DB����
			listSanpuru = this.searchDB.dbSearch(strSQL.toString());
			
			//�������
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append(" T120.cd_shain ");		//0
			strSQL.append(" ,T120.nen ");			//1
			strSQL.append(" ,T120.no_oi ");			//2
			strSQL.append(" ,T120.cd_kotei ");		//3
			strSQL.append(" ,T120.seq_kotei ");		//4
			strSQL.append(" ,T320.nm_genryo ");		//5
			strSQL.append(" ,T320.tanka_ins ");		//6
			strSQL.append(" ,T320.budomari_ins ");	//7
			strSQL.append(" ,T320.tanka_ma ");		//8
			strSQL.append(" ,T320.budomar_ma ");	//9
			strSQL.append(" ,T120.cd_genryo ");		//10
			strSQL.append(" ,ISNULL(T320.tanka_ins,T320.tanka_ma) AS tankaD ");		//11
			strSQL.append(" ,ISNULL(T320.budomari_ins,T320.budomar_ma) AS budomarD ");	//12
			strSQL.append(" ,T120.sort_kotei ");	//13
			strSQL.append(" ,T120.sort_genryo ");	//14
			strSQL.append(" ,T120.tanka ");			//15
			strSQL.append(" ,T120.budomari ");		//16
			strSQL.append(" ,T120.nm_kotei ");		//17
			strSQL.append(" ,M302.nm_literal ");	//18
			
			
//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/2/16 START -----------------------------------
			
			//�P���@�H�ꖼ
			strSQL.append(" ,CASE WHEN T320.tanka_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,M104_tanka.nm_busho) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as nm_kojo_tanka ");	//19
			
			//�P���@��ЃR�[�h�i�}�X�^�j
			strSQL.append(" ,CASE WHEN T320.tanka_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,T320.cd_kaisha_tanka) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as cd_kaisha_tanka ");	//20

			//�P���@�H��R�[�h�i�}�X�^�j
			strSQL.append(" ,CASE WHEN T320.tanka_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,T320.cd_kojo_tanka) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as cd_kojo_tanka ");	//21

			//������@�H�ꖼ
			strSQL.append(" ,CASE WHEN T320.budomari_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,M104_budomari.nm_busho) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as nm_kojo_budomari ");	//22

			//������@��ЃR�[�h�i�}�X�^�j
			strSQL.append(" ,CASE WHEN T320.budomari_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,T320.cd_kaisha_budomari) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as cd_kaisha_budomari ");	//23

			//������@�H��R�[�h�i�}�X�^�j
			strSQL.append(" ,CASE WHEN T320.budomari_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,T320.cd_kojo_budomari) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as cd_kojo_budomari ");	//24
			
//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/2/16 END --------------------------------------
			
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_haigo AS T120 ");
			strSQL.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			strSQL.append(" ON  T120.cd_shain = T320.cd_shain ");
			strSQL.append(" AND T120.nen      = T320.nen ");
			strSQL.append(" AND T120.no_oi    = T320.no_oi ");
			strSQL.append(" AND T120.cd_kotei = T320.cd_kotei ");
			strSQL.append(" AND T120.seq_kotei= T320.seq_kotei ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kote' ");
			strSQL.append(" AND T120.zoku_kotei  = M302.cd_literal ");
			
			
//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/2/16 START -----------------------------------			
			
			//�P���@�H�ꖼ
			strSQL.append(" LEFT JOIN ma_busho AS M104_tanka ");
			strSQL.append(" 	ON T320.cd_kaisha_tanka = M104_tanka.cd_kaisha ");
			strSQL.append(" 	AND T320.cd_kojo_tanka = M104_tanka.cd_busho ");

			//������@�H�ꖼ
			strSQL.append(" LEFT JOIN ma_busho AS M104_budomari ");
			strSQL.append(" 	ON T320.cd_kaisha_budomari = M104_budomari.cd_kaisha ");
			strSQL.append(" 	AND T320.cd_kojo_budomari = M104_budomari.cd_busho ");
			
//�y�V�T�N�C�b�N�i�����j�v�]�z�P���E�����@�H�ꖼ�擾�@TT.Nishigawa 2010/2/16 END --------------------------------------
			
			
//			strSQL.append(" WHERE ");
//			strSQL.append("     T120.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
//			strSQL.append(" AND T120.nen       = " + toString(cd_shisaku_nen) + " ");
//			strSQL.append(" AND T120.no_oi     = " + toString(cd_shisaku_oi) + " ");
//			strSQL.append(" ORDER BY ");
//			strSQL.append("  T120.cd_shain ");
//			strSQL.append(" ,T120.nen ");
//			strSQL.append(" ,T120.no_oi ");
//			strSQL.append(" ,T120.sort_kotei ");
//			strSQL.append(" ,T120.sort_genryo ");
			
			//�yQP@00342�z
			strSQL.append(" WHERE ");
			strSQL.append("     T320.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T320.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T320.no_oi     = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T320.no_eda     = " + toString(cd_shisaku_eda) + " ");
			strSQL.append(" ORDER BY ");
			strSQL.append("  T120.cd_shain ");
			strSQL.append(" ,T120.nen ");
			strSQL.append(" ,T120.no_oi ");
			strSQL.append(" ,T120.sort_kotei ");
			strSQL.append(" ,T120.sort_genryo ");
			
			//DB����
			listGenryo = this.searchDB.dbSearch(strSQL.toString());

			//���ޏ��
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  cd_shain ");	//0
			strSQL.append(" ,nen ");		//1
			strSQL.append(" ,no_oi ");		//2
			strSQL.append(" ,seq_shizai ");	//3
			strSQL.append(" ,no_sort ");	//4
			strSQL.append(" ,cd_kaisha ");	//5
			strSQL.append(" ,cd_busho  ");	//6
			strSQL.append(" ,cd_shizai ");	//7
			strSQL.append(" ,nm_shizai ");	//8
			strSQL.append(" ,tanka ");		//9
			strSQL.append(" ,budomari ");	//10
			strSQL.append(" ,cs_siyou ");	//11
			strSQL.append(" ,id_toroku ");	//12
			strSQL.append(" ,CONVERT(VARCHAR,dt_toroku,111) ");	//13
			strSQL.append(" ,id_koshin ");	//14
			strSQL.append(" ,CONVERT(VARCHAR,dt_koshin,111) ");	//15
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shizai ");
			strSQL.append(" WHERE ");
			strSQL.append("     cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND no_oi    = " + toString(cd_shisaku_oi) + " ");
			//�yQP@00342�z
			strSQL.append(" AND no_eda    = " + toString(cd_shisaku_eda) + " ");
			strSQL.append(" ORDER BY ");
			strSQL.append("  no_sort ");
			//DB����
			listShizai = this.searchDB.dbSearch(strSQL.toString());
			
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			//�T���v�����̊�{���
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL����
			strSQL.append("SELECT T313.cd_shain , ");
			strSQL.append("T313.nen , ");
			strSQL.append("T313.no_oi , ");
			strSQL.append("T313.no_eda , ");
			strSQL.append("T313.seq_shisaku, ");
			strSQL.append("T313.genka , ");
			strSQL.append("T313.baika , ");
			strSQL.append("T313.cd_genka_tani ");
			strSQL.append("FROM tr_shisan_kihonjoho AS T313 ");
			strSQL.append("LEFT JOIN tr_shisakuhin AS T110 ");
			strSQL.append("ON T313.cd_shain = T110.cd_shain ");
			strSQL.append("AND T313.nen = T110.nen ");
			strSQL.append("AND T313.no_oi = T110.no_oi ");
			strSQL.append("INNER JOIN tr_shisaku AS T131 ");
			strSQL.append("ON T313.cd_shain = T131.cd_shain ");
			strSQL.append("AND T313.nen = T131.nen ");
			strSQL.append("AND T313.no_oi = T131.no_oi ");
			strSQL.append("AND T313.seq_shisaku = T131.seq_shisaku ");
			strSQL.append("WHERE T313.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append("AND T313.nen = " + toString(cd_shisaku_nen) + " ");
			strSQL.append("AND T313.no_oi = " + toString(cd_shisaku_oi) + " ");
			strSQL.append("AND T313.no_eda = " + toString(cd_shisaku_eda) + " ");
			strSQL.append("ORDER BY ");
			strSQL.append("  T131.cd_shain ");
			strSQL.append(" ,T131.nen ");
			strSQL.append(" ,T131.no_oi ");
			strSQL.append(" ,T131.sort_shisaku ");
			
			//DB����
			listKihonSub = this.searchDB.dbSearch(strSQL.toString());
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B \nSQL:"
					+ strSQL.toString());
			
		}finally{
			//DB�R�l�N�V�����J��
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
				
			}
			//���[�J���ϐ��̊J��
			strSQL = null;

		}
		
	}
	/**
	 * ��{���N�G�X�g�̐���
	 * @param reqDataKeisan	�F�������ʁ@���N�G�X�g
	 * @param listShisaku	�FDB���@����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Kihon(

			 RequestResponsKindBean reqDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try{

			Object[] items = (Object[]) listShisaku.get(0);
			
			// ----------------------------------------------------------------------+
			// ����CD-�Ј�CD	cd_shain
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "cd_shain", toString(items[0]));
			// ----------------------------------------------------------------------+
			// ����CD-�N		nen
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "nen", toString(items[1]));
			// ----------------------------------------------------------------------+
			// ����CD-�ǔ�	no_oi
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "no_oi", toString(items[2]));
			
			//�yQP@00342�z
			// ----------------------------------------------------------------------+
			// ����CD-�}��	no_eda
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "no_eda", toString(items[8]));
			
			
			// ----------------------------------------------------------------------+
			// ���萔			irisu
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "irisu", toString(items[3]));
			// ----------------------------------------------------------------------+
			// �������[�h		mode
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "mode", "1");
			// ----------------------------------------------------------------------+
			// ������]		kibo_genka
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_genka", toString(items[4]));
			// ----------------------------------------------------------------------+
			// ������]		kibo_baika
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_baika", toString(items[5]));
			// ----------------------------------------------------------------------+
			// �����P��CD	kibo_genka_tani
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_genka_tani", toString(items[6]));
			// ----------------------------------------------------------------------+
			// �v�Z���ځi�Œ��/�P�[�Xor�Œ��/kg�j	ragio_kesu_kg
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "ragio_kesu_kg", toString(items[7]));
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B ");
			
		}finally{
			
		}
		
	}
	/**
	 * �������N�G�X�g�̐���
	 * @param reqDataKeisan	�F�ҏW�ナ�N�G�X�g
	 * @param listGenryo	�F�������
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Genryo(

			 RequestResponsKindBean reqDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try{

			for(int i = 0 ; i < listGenryo.size(); i++){

				Object[] items = (Object[]) listGenryo.get(i);

				// ----------------------------------------------------------------------+
				// �H��CD	cd_kotei
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "cd_kotei", toString(items[3]));
				// ----------------------------------------------------------------------+
				// �H��SEQ	seq_kotei
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "seq_kotei", toString(items[4]));
				// ----------------------------------------------------------------------+
				// ����CD	cd_genryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "cd_genryo", toString(items[10]));
				// ----------------------------------------------------------------------+
				// �P��		tanka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "tanka", toString(items[11]));
				// ----------------------------------------------------------------------+
				// ����		budomari
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "budomari", toString(items[12]));
			}
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B ");
			
		}finally{
			
		}
		
	}
	/**
	 * �v�Z���N�G�X�g�̐���
	 * @param reqDataKeisan	:��������
	 * @param listSanpuru	:�T���v�����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Keisan(

			 RequestResponsKindBean reqDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try{

			for(int i = 0 ; i < listSanpuru.size(); i++){

				Object[] items = (Object[]) listSanpuru.get(i);
				
				// ----------------------------------------------------------------------+
				// ����SEQ		seq_shisaku
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "seq_shisaku", toString(items[3]));
				// ----------------------------------------------------------------------+
				// �L�������i���j	yuuko_budomari
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "yuuko_budomari", toString(items[12]));
				// ----------------------------------------------------------------------+
				// ���Ϗ[�U�ʁi���j	heikinjyutenryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "heikinjyutenryo", toString(items[13]));
				// ----------------------------------------------------------------------+
				// �Œ��/�P�[�X	kesu_kotehi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kesu_kotehi", toString(items[14]));
				// ----------------------------------------------------------------------+
				// �Œ��/KG		kg_kotehi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_kotehi", toString(items[15]));
				// ADD 2013/11/1 QP@30154 okano start
				// ----------------------------------------------------------------------+
				// �Œ��/�P�[�X	kesu_kotehi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kesu_rieki", toString(items[16]));
				// ----------------------------------------------------------------------+
				// �Œ��/KG		kg_kotehi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_rieki", toString(items[17]));
				// ADD 2013/11/1 QP@30154 okano end
				
				
//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38			
				// ----------------------------------------------------------------------+
				// �����H����		seizokotei_han
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "seizokotei_han", toString(items[18]));
				
				// ----------------------------------------------------------------------+
				// �����H��		seizokotei_shosai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "seizokotei_shosai", toString(items[19]));
//mod end --------------------------------------------------------------------------------
				
				//�yQP@00342�z
				// ----------------------------------------------------------------------+
				// ���Z���~	fg_chusi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "fg_chusi", toString(items[20]));
				
//ADD 2013/07/9 ogawa �yQP@30151�zNo.13 start
				// ----------------------------------------------------------------------+
				// ���ڌŒ�`�F�b�N	fg_koumokuchk
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "fg_koumokuchk", toString(items[21]));
//ADD 2013/07/09 ogawa �yQP@30151�zNo.13 end
				
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start	
				// ----------------------------------------------------------------------+
				// �[�U�ʐ����i���j	zyusui
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "zyusui", toString(items[22]));
				
				// ----------------------------------------------------------------------+
				// �[�U�ʖ����i���j	zyuabura
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "zyuabura", toString(items[23]));
				
				// ----------------------------------------------------------------------+
				// ���v�ʁi���j 	gokei
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "gokei", toString(items[24]));
				
				// ----------------------------------------------------------------------+
				// ��d 			hiju
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "hiju", toString(items[25]));
				
				// ----------------------------------------------------------------------+
				// ���x���ʁi�s�j 	reberu
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "reberu", toString(items[26]));
				
				// ----------------------------------------------------------------------+
				// ��d���Z�ʁi�s�j  hijukasan
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "hijukasan", toString(items[27]));
				
				// ----------------------------------------------------------------------+
				// ������i�~�j/�P�[�X  cs_genryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "cs_genryo", toString(items[28]));
				
				// ----------------------------------------------------------------------+
				// �ޗ���i�~�j/�P�[�X  cs_zairyohi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "cs_zairyohi", toString(items[29]));
				
				// ----------------------------------------------------------------------+
				// �����v�i�~�j/�P�[�X  cs_genka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "cs_genka", toString(items[30]));
				
				// ----------------------------------------------------------------------+
				// �����v�i�~�j/��  ko_genka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "ko_genka", toString(items[31]));
				
				// ----------------------------------------------------------------------+
				// ������i�~�j/�s   kg_genryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_genryo", toString(items[32]));
				
				// ----------------------------------------------------------------------+
				// �ޗ���i�~�j/�s   kg_zairyohi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_zairyohi", toString(items[33]));
				
				// ----------------------------------------------------------------------+
				// �����v�i�~�j/�s   kg_genka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_genka", toString(items[34]));
				
				// ----------------------------------------------------------------------+
				// ����   			 baika
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "baika", toString(items[35]));
				
				// ----------------------------------------------------------------------+
				// �e���i���j    	 arari
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "arari", toString(items[36]));
				
//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
				
			}
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B ");
			
		}finally{
			
		}
		
	}
	/**
	 * ���ރ��N�G�X�g�̐���
	 * @param reqDataKeisan	:�ҏW���ʃ��N�G�X�g
	 * @param listShizai	:���ޏ��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Shizai(

			 RequestResponsKindBean reqDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try{

			for(int i = 0 ; i < listShizai.size(); i++){

				Object[] items = (Object[]) listShizai.get(i);

				// ----------------------------------------------------------------------+
				// ����SEQ	seq_shizai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "seq_shizai", toString(items[3]));
				// ----------------------------------------------------------------------+
				// ���CD	cd_kaisya
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "cd_kaisya", toString(items[5]));
				// ----------------------------------------------------------------------+
				// �H��CD	cd_kojyo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "cd_kojyo", toString(items[6]));
				// ----------------------------------------------------------------------+
				// ����CD	cd_shizai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "cd_shizai", toString(items[7]));
				// ----------------------------------------------------------------------+
				// ���ޖ�	nm_shizai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "nm_shizai", toString(items[8]));
				// ----------------------------------------------------------------------+
				// �P��		tanka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "tanka", toString(items[9]));
				// ----------------------------------------------------------------------+
				// �����i���j	budomari
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "budomari", toString(items[10]));
				// ----------------------------------------------------------------------+
				// �g�p��/���	shiyouryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "shiyouryo", toString(items[11]));
				// ----------------------------------------------------------------------+
				// �X�V��ID	ID_koshin
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "ID_koshin", toString(items[14]));
				// ----------------------------------------------------------------------+
				// �X�V���t	DT_koshin
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "DT_koshin", toString(items[15]));

			}
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B ");
			
		}finally{
			
		}
		
	}
	
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	/**
	 * ��{���T�u���N�G�X�g�̐���
	 * @param reqDataKeisan	:��������
	 * @param listSanpuru	:�T���v�����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKihon_Sub(

			 RequestResponsKindBean reqDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try{

			for(int i = 0 ; i < listKihonSub.size(); i++){

				Object[] items = (Object[]) listKihonSub.get(i);
				// ----------------------------------------------------------------------+
				// ����CD-�Ј�CD	cd_shain
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "cd_shain", toString(items[0]));
				// ----------------------------------------------------------------------+
				// ����CD-�N		nen
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "nen", toString(items[1]));
				// ----------------------------------------------------------------------+
				// ����CD-�ǔ�	no_oi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "no_oi", toString(items[2]));
				// ----------------------------------------------------------------------+
				// ����CD-�}��	no_eda
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "no_eda", toString(items[3]));
				// ----------------------------------------------------------------------+
				// ����SEQ		seq_shisaku
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "seq_shisaku", toString(items[4]));
				// ----------------------------------------------------------------------+
				// ������]		kibo_genka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "kibo_genka", toString(items[5]));
				// ----------------------------------------------------------------------+
				// ������]		kibo_baika
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "kibo_baika", toString(items[6]));
				// ----------------------------------------------------------------------+
				// �����P��CD	kibo_genka_tani
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "kibo_genka_tani", toString(items[7]));
				
			}
		
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z��{���T�u�\���@DB���擾�Ɏ��s���܂����B ");
			
		}finally{
			
		}
		
	}
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
	
}