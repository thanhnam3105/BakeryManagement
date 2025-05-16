package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

import java.util.List;

/**
 *
 * ���������i��ۯ���޳ݗp�jDB����
 *  : ��ۯ���޳ݗp�@���������f�[�^���oSQL���쐬����B
 * M104_busho�e�[�u������A�f�[�^�̒��o���s���B
 *
 * @author TT.katayama
 * @since 2009/04/08
 */
public class BushoSearchLogic extends LogicBase {

	/**
	 * ���������i��ۯ���޳ݗp�j�p�R���X�g���N�^ : �C���X�^���X����
	 */
	public BushoSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F�������擾SQL�쐬
	 *  M104_busho�e�[�u������A�f�[�^�̒��o���s���B
	 *
	 * @param reqKind : �@�\���N�G�X�g�f�[�^
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

		//���N�G�X�g�f�[�^
		String strKinoId = "";
		String strTableNm = "";
		String strReqUserId = "";
		String strReqGamenId = "";
		String strReqKaishaCd = "";
		//���[�U���
		String strUDataId = "";
		String strUKinoId = "";

		List<?> lstRecset = null;
		StringBuffer strSql = new StringBuffer();

		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�����f�[�^ID�̎擾

			//�@�F�@�\���N�G�X�g�f�[�^�N���X��p���A���e�����}�X�^�����擾����SQL���쐬����B

			//�@�\���N�G�X�g�f�[�^��胆�[�UID�Ɖ��ID���擾
			strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strReqGamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//���[�U���̃��[�UID���擾
			strReqUserId = userInfoData.getId_user();

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strReqGamenId)){
					//�@�\ID��ݒ�
					strUKinoId = userInfoData.getId_kino().get(i).toString();
					//�f�[�^ID��ݒ�
					strUDataId = userInfoData.getId_data().get(i).toString();
				}
			}

			// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
			//�����[�U�����݂��Ȃ�
			if (strReqUserId.equals("")) {
				this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "E000205", "", "", "");
			}
			// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end

			//���ID�A�@�\ID�A�f�[�^ID�ɂ�鏈���̐���
			if ( strReqGamenId.equals("10") ) {
				//����f�[�^�ꗗ�iJSP�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���i�{�l+���ذ�ް�ȏ�j
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����
					} else if ( strUDataId.equals("3") ) {
						//�S�����
					} else if ( strUDataId.equals("4") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}




//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
			else if ( strReqGamenId.equals("20") ) {
				//�������͏��iJSP�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("2") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�폜�̂݁j
					if ( strUDataId.equals("2") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else if ( strUKinoId.equals("50") ) {		//�ҏW�i�폜�{CSV�o�́j
					if ( strUDataId.equals("2") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}

			else if ( strReqGamenId.equals("30") ) {
				//���͒l���́i�V�K�j�iJSP�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("30") ) {					//�V�K
					if ( strUDataId.equals("1") ) {
						//�S�����


					} else if ( strUDataId.equals("2") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}

			else if ( strReqGamenId.equals("40") ) {
				//���͒l���́i�ڍׁj�iJSP�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//�S�����


					} else if ( strUDataId.equals("2") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW
					if ( strUDataId.equals("1") ) {
						//�S�����


					} else if ( strUDataId.equals("2") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}
//mod end   -------------------------------------------------------------------------------



			else if ( strReqGamenId.equals("50") ) {
				//�S�H��P��������ʁiJSP�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j

					} else if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			} else if ( strReqGamenId.equals("60") ) {
				//�e���e�����}�X�^�iJSP�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {					//�ҏW�i�X�V�̂݁j
					if ( strUDataId.equals("1") ) {
						//����O���[�v

					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);

					}
				} else if ( strUKinoId.equals("50") ) {		//�ҏW�i�X�V�{CSV�o�́j
					if ( strUDataId.equals("1") ) {
						//����O���[�v

					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);

					}
				} else  if ( strUKinoId.equals("40") ) {		//�ҏW�i�V�X�e���Ǘ��ҁj
					if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			} else if ( strReqGamenId.equals("70") ) {
				//�O���[�v�}�X�^�iJSP�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {					//�ҏW
					if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			} else if ( strReqGamenId.equals("80") ) {
				//�����}�X�^�iJSP�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {					//�ҏW
					if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			} else if ( strReqGamenId.equals("90") ) {
				//�S���҃}�X�^�iJSP�j

				//��ЃR�[�h�������͂̏ꍇ�i���[�UID�̃��X�g�t�H�[�J�X���j
				if (strReqKaishaCd.equals("")) {
					//���[�UID�̍Ď擾
					strReqUserId = reqData.getFieldVale(0, 0, "id_user");
				}

				//�����ɂ�镪��
				if ( strUKinoId.equals("21") ) {					//�ҏW�i�����߽ܰ�ނ̂݁j
					if ( strUDataId.equals("1") ) {
						//�����̂�
						strSql = this.createSQLMyBushoTantoMst(strReqUserId, strReqKaishaCd);

					// ADD 2013/10/24 QP@30154 okano start
					} else if ( strUDataId.equals("2") ) {
						//������Ђ̂�
						strSql = this.createSQLAllBushoTantoMst(strReqUserId, strReqKaishaCd);

					// ADD 2013/10/24 QP@30154 okano end
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBushoTantoMst(strReqUserId, strReqKaishaCd);

					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�S�āj
					if ( strUDataId.equals("1") ) {
						//�����̂�
						strSql = this.createSQLMyBushoTantoMst(strReqUserId, strReqKaishaCd);

					// ADD 2013/10/24 QP@30154 okano start
					} else if ( strUDataId.equals("2") ) {
						//������Ђ̂�
						strSql = this.createSQLAllBushoTantoMst(strReqUserId, strReqKaishaCd);

					// ADD 2013/10/24 QP@30154 okano end
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBushoTantoMst(strReqUserId, strReqKaishaCd);

					}
				// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
				} else if ( strUKinoId.equals("22") || strUKinoId.equals("23") ) {		//�ҏW�i�S�āj
						//�S��
						strSql = this.createSQLAllBushoTantoMst(strReqUserId, strReqKaishaCd);
				} else if ( strUKinoId.equals("24") ) {		//�ҏW�i�S�āj
						//�S��
						strSql = this.createSQLCashBushoTantoMst(strReqUserId, strReqKaishaCd);
				// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}


//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
			else if ( strReqGamenId.equals("000") ) {
				//����f�[�^��ʁi�{���j(JWS)

				//�S��
				//strSql = this.createSQLAllBusho(strReqKaishaCd);
				strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

			}
			else if ( strReqGamenId.equals("100") ) {
				//����f�[�^��ʁi�ڍׁj(JWS)

				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
//					if ( strUDataId.equals("1") ) {
//						//����O���[�v���i�{�l+���ذ�ް�ȏ�j
//						strSql = this.createSQLAllBusho(strReqKaishaCd);
//
//					} else if ( strUDataId.equals("2") ) {
//						//����O���[�v���S�����
//						strSql = this.createSQLAllBusho(strReqKaishaCd);
//
//					} else if ( strUDataId.equals("3") ) {
//						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
//						strSql = this.createSQLAllBusho(strReqKaishaCd);
//
//					} else if ( strUDataId.equals("4") ) {
//						//������
//						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
//
//					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

//					}
				}
				else if ( strUKinoId.equals("20") ) {			//�ҏW

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
//					if ( strUDataId.equals("1") ) {
					if ( strUDataId.equals("1") || strUDataId.equals("5") ) {
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end

						//����O���[�v���i�{�l+���ذ�ް�ȏ�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);


					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					} else if ( strUDataId.equals("4") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo_3(strReqUserId, strReqKaishaCd);


					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}
			else if ( strReqGamenId.equals("110") ) {
				//����f�[�^��ʁi�V�K�j(JWS)

				//�����ɂ�镪��
				if ( strUKinoId.equals("30") ) {			//�V�K
					if ( strUDataId.equals("1") ) {
						//����O���[�v���i�{�l+���ذ�ް�ȏ�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					} else if ( strUDataId.equals("4") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo_3(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}
			else if ( strReqGamenId.equals("120") ) {
				//����f�[�^��ʁi���@�x���R�s�[�j(JWS)

				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {			//�ҏW�i�V�K�j
					if ( strUDataId.equals("1") ) {
						//����O���[�v���i�{�l+���ذ�ް�ȏ�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					} else if ( strUDataId.equals("4") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo_3(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo_3(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}
			else if ( strReqGamenId.equals("130") ) {
				//���͒l���́i�V�K�j�iJWS�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�X�V�j
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}
			else if ( strReqGamenId.equals("140") ) {
				//���͒l���́i�ڍׁj�iJWS�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			} else if ( strReqGamenId.equals("150") ) {
				//�����ꗗ

				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					} else if ( strUDataId.equals("2") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			} else if ( strReqGamenId.equals("160") ) {
				//������񕪐́iJWS�j

				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					} else if ( strUDataId.equals("2") ) {
						//������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�폜�j
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					} else if ( strUDataId.equals("2") ) {
						//��������
						//strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						strSql = this.createSQLMyBusho_DaihyoKojo(strReqUserId, strReqKaishaCd);

					} else if ( strUDataId.equals("9") ) {
						//�S��
						//strSql = this.createSQLAllBusho(strReqKaishaCd);
						strSql = this.createSQLAllBusho_DaihyoKojo(strReqKaishaCd);

					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end
				}
			}
//mod end   -------------------------------------------------------------------------------

			//�yQP@00342�z�������Z�ꗗ
			else if ( strReqGamenId.equals("180") ) {

				//�{��
				if ( strUKinoId.equals("80") ) {
					//���H�ꕪ�̂�
					if ( strUDataId.equals("2") ) {
						strReqKaishaCd = userInfoData.getCd_kaisha();
						strSql = this.createSQLMyBusho(strReqUserId,strReqKaishaCd);

					}
					//���H�ꕪ�̂݁@�ȊO
					else {
						if(toString(strReqKaishaCd).equals("")){
							strSql = this.createSQLAllBusho_KojoFg("1");
						}
						else{
							strSql = this.createSQLAllBusho_KojoFg(strReqKaishaCd);
						}
					}
				}
				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 start
				else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000410", strReqGamenId, strUKinoId, "");
				}
				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.32 end

			}
			// 2016/10/05 �yKPX@1602367�z�ގ��f�[�^�ďo��� add
			// 2014/09/17 �yQP@40404�z�f�U�C���X�y�[�X�o�^�E�_�E�����[�h��ʁA���ގ�z���͉�� TT E.Kitazawa add start
			else if (strReqGamenId.equals("240") || strReqGamenId.equals("250") || strReqGamenId.equals("260")
					|| strReqGamenId.equals("290")  || strReqGamenId.equals("330") || strReqGamenId.equals("340")) {
				// ���ЍH��
				strReqKaishaCd = userInfoData.getCd_kaisha();
				strSql = this.createSQLAllBusho_Kojo(strReqKaishaCd);
			}
			// 2014/09/17 �yQP@40404�z�f�U�C���X�y�[�X�o�^��� TT E.Kitazawa add end

			//�A�F�f�[�^�x�[�X������p���A�����R���{�{�b�N�X�f�[�^���擾����B
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//�B�F�R���{�p�F�����p�����[�^�[�i�[���\�b�h���ďo���A�@�\���X�|���X�f�[�^���`������B

			// �@�\ID�̐ݒ�
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// �e�[�u�����̐ݒ�
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̌`��
			storageKaishaCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "�R���{�p�F�������擾SQL�쐬���������s���܂����B");
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

		//�C�F�@�\���X�|���X�f�[�^��ԋp����B
		return resKind;
	}

	/**
	 * �����u�S�āv�̕����f�[�^�擾�pSQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLAllBusho(String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL���̍쐬
		strRetSql.append("SELECT distinct cd_busho, nm_busho");
		strRetSql.append(" FROM ma_busho");
		strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);
		strRetSql.append(" ORDER BY cd_busho");

		return strRetSql;
	}

	/**�yQP@00342�z
	 * �����u�S�āv�̕����f�[�^�擾�pSQL�쐬�i�H��t���O���P�̂��́j
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLAllBusho_KojoFg(String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL���̍쐬
		strRetSql.append("SELECT distinct cd_busho, nm_busho");
		strRetSql.append(" FROM ma_busho");
		strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);

		//�yQP@00342�z�H��t���O���P�̂��̂����\�� + ������
		strRetSql.append(" AND	flg_kojo = 1 ");
		//MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//		strRetSql.append(" OR (cd_kaisha = 1 AND cd_busho=1) ");
		// ��ЃR�[�h���L���[�s�[�̎��̂݁A��������ǉ�
		if ("1".equals(strKaishaCd) ) {
			strRetSql.append(" OR (cd_kaisha = 1 AND cd_busho=1) ");
		}
		//MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

		strRetSql.append(" ORDER BY cd_busho");

		return strRetSql;
	}

	/**�yQP@40404�z
	 * �����u�S�āv�̕����f�[�^�擾�pSQL�쐬�i�H��t���O���P�̂��́j
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLAllBusho_Kojo(String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL���̍쐬
		strRetSql.append("SELECT distinct cd_busho, nm_busho");
		strRetSql.append(" FROM ma_busho");
		strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);

		strRetSql.append(" AND	flg_kojo = 1 ");

		strRetSql.append(" ORDER BY cd_busho");

		return strRetSql;
	}

	/**
	 * �����u���������v�̕����f�[�^�擾�pSQL�쐬
	 * @param strUserId : ���[�UID
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLMyBusho(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		String strBushoCd = userInfoData.getCd_busho();

		// SQL���̍쐬
		strRetSql.append(" SELECT distinct ");
		strRetSql.append(" M101.cd_busho AS cd_busho,  ");
		strRetSql.append(" ISNULL(M104.nm_busho,'') AS nm_busho ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_busho = M101.cd_busho ");
		strRetSql.append("   AND M104.cd_kaisha = M101.cd_kaisha ");
		strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
		strRetSql.append("  AND M101.id_user =" + strUserId);

		return strRetSql;
	}

	/**
	 * �S���҃}�X�^��p�F�����u�S�āv�̕����f�[�^�擾�pSQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLAllBushoTantoMst(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL���̍쐬
		strRetSql.append("SELECT distinct cd_busho, nm_busho");
		strRetSql.append(" FROM ma_busho");
		if (strKaishaCd.equals("")) {
			// MOD 2013/11/1 okano�yQP@30151�zNo.28 start
//				strRetSql.append(" WHERE cd_kaisha = (SELECT cd_kaisha FROM ma_user WHERE id_user = " + strUserId + ")");
			strRetSql.append(" WHERE cd_kaisha = (SELECT CASE ");
			strRetSql.append(" 	WHEN (SELECT COUNT(cd_kaisha) FROM ma_user WHERE id_user = " + strUserId + " ) = 0");
			strRetSql.append(" 		THEN (SELECT cd_kaisha FROM ma_user_new WHERE id_user = " + strUserId + " ) ");
			strRetSql.append(" 	WHEN (SELECT COUNT(cd_kaisha) FROM ma_user WHERE id_user = " + strUserId + " ) > 0");
			strRetSql.append(" 		THEN (SELECT cd_kaisha FROM ma_user WHERE id_user = " + strUserId + " ) ");
			strRetSql.append(" 	END ) ");
			// MOD 2013/11/1 okano�yQP@30151�zNo.28 end
		} else {
			strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);
		}

		// ADD 2013/6/18 okano�yQP@30151�zNo.10 start //
		strRetSql.append(" AND (flg_eigyo IS NULL OR flg_eigyo <> 1) ");
		// ADD 2013/6/18 okano�yQP@30151�zNo.10 end //

		strRetSql.append(" ORDER BY cd_busho");

		return strRetSql;
	}

	// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
	private StringBuffer createSQLCashBushoTantoMst(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL���̍쐬
		strRetSql.append("SELECT distinct cd_busho, nm_busho");
		strRetSql.append(" FROM ma_busho");
		if (strKaishaCd.equals("")) {
			strRetSql.append(" WHERE cd_kaisha = (SELECT cd_kaisha FROM ma_user_new WHERE id_user = " + strUserId + ")");
		} else {
			strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);
		}

		strRetSql.append(" AND (flg_eigyo IS NULL OR flg_eigyo <> 1) ");

		strRetSql.append(" ORDER BY cd_busho");

		return strRetSql;
	}
	// ADD 2013/9/25 okano�yQP@30151�zNo.28 end

	/**
	 * �S���҃}�X�^��p�F�����u�����̂݁v�̕����f�[�^�擾�pSQL�쐬
	 * @param strUserId : ���[�UID
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLMyBushoTantoMst(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		String strBushoCd = userInfoData.getCd_busho();

		// SQL���̍쐬
		strRetSql.append(" SELECT distinct ");
		strRetSql.append(" M101.cd_busho AS cd_busho,  ");
		strRetSql.append(" ISNULL(M104.nm_busho,'') AS nm_busho ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_kaisha = M101.cd_kaisha ");
		strRetSql.append("   AND M104.cd_busho = " + strBushoCd);
		if (strKaishaCd.equals("")) {
			strRetSql.append(" WHERE M101.id_user =" + strUserId);
		} else {
			strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
			strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
			strRetSql.append("  AND M101.id_user =" + strUserId);
		}

		// ADD 2013/6/18 okano�yQP@30151�zNo.10 start //
		strRetSql.append(" AND (M104.flg_eigyo IS NULL OR M104.flg_eigyo <> 1) ");
		// ADD 2013/6/18 okano�yQP@30151�zNo.10 end //

		return strRetSql;
	}





	/**
	 * �R���{�p�F�����p�����[�^�[�i�[
	 *  : �����R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA290O�v�ɐݒ肷��B
	 *
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageKaishaCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {

			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstGroupCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_busho", items[0].toString());
				resTable.addFieldVale(i, "nm_busho", items[1].toString());

			}

			if (lstGroupCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_busho", "");
				resTable.addFieldVale(0, "nm_busho", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�R���{�p�F�����p�����[�^�[�i�[���������s���܂����B");

		} finally {

		}

	}

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.6
	/**
	 * �����u���������v�̕����f�[�^�擾�pSQL�쐬
	 * @param strUserId : ���[�UID
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLMyBusho_DaihyoKojo(String strUserId, String strKaishaCd)  throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strRetSql = new StringBuffer();

		String strBushoCd = userInfoData.getCd_busho();

		if(strKaishaCd.equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KAISHA"))
				&& strBushoCd.equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KOJO"))){
			// SQL���̍쐬
			strRetSql.append(" SELECT distinct ");
			strRetSql.append(" M101.cd_busho AS cd_busho,  ");
			strRetSql.append(" '�V�K�o�^����' AS nm_busho ");
			strRetSql.append(" FROM ma_user M101 ");
			strRetSql.append("  LEFT JOIN ma_busho M104 ");
			strRetSql.append("   ON M104.cd_busho = M101.cd_busho ");
			strRetSql.append("   AND M104.cd_kaisha = M101.cd_kaisha ");
			strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
			strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
			strRetSql.append("  AND M101.id_user =" + strUserId);
			strRetSql.append("  AND fg_hyoji = 1");
		}else{
			// SQL���̍쐬
			strRetSql.append(" SELECT distinct ");
			strRetSql.append(" M101.cd_busho AS cd_busho,  ");
			strRetSql.append(" ISNULL(M104.nm_busho,'') AS nm_busho ");
			strRetSql.append(" FROM ma_user M101 ");
			strRetSql.append("  LEFT JOIN ma_busho M104 ");
			strRetSql.append("   ON M104.cd_busho = M101.cd_busho ");
			strRetSql.append("   AND M104.cd_kaisha = M101.cd_kaisha ");
			strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
			strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
			strRetSql.append("  AND M101.id_user =" + strUserId);
			strRetSql.append("  AND fg_hyoji = 1");
		}
		return strRetSql;
	}

	/**
	 * �����u�S�āv�̕����f�[�^�擾�pSQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLAllBusho_DaihyoKojo(String strKaishaCd)  throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strRetSql = new StringBuffer();

		if(strKaishaCd.equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KAISHA"))){
			strRetSql.append(" select");
			strRetSql.append(" distinct A.cd_busho");
			strRetSql.append(" ,A.nm_busho");
			strRetSql.append(" FROM");
			strRetSql.append(" (	SELECT ");
			strRetSql.append("	 distinct cd_busho, nm_busho");
			strRetSql.append("	FROM ma_busho");
			strRetSql.append("	 WHERE cd_kaisha =" + strKaishaCd +  " AND cd_busho <> " + ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KOJO") );
			strRetSql.append("     AND fg_hyoji = 1");
			strRetSql.append("	union all");
			strRetSql.append("	select");
			strRetSql.append("	 distinct cd_busho, '�V�K�o�^����' AS nm_busho");
			strRetSql.append("	from ma_busho ");
			strRetSql.append("	 WHERE cd_kaisha =" + strKaishaCd +  " AND cd_busho = " + ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KOJO"));
			strRetSql.append("     AND fg_hyoji = 1");
			strRetSql.append(" ) AS A");
			strRetSql.append(" ORDER BY cd_busho");
		}
		else{
			// SQL���̍쐬
			strRetSql.append("SELECT distinct cd_busho, nm_busho");
			strRetSql.append(" FROM ma_busho");
			strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);
			strRetSql.append("  AND fg_hyoji = 1");
			strRetSql.append(" ORDER BY cd_busho");
		}
		return strRetSql;
	}

	/**
	 * �����u���������v�̕����f�[�^�擾�pSQL�쐬
	 * @param strUserId : ���[�UID
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLMyBusho_DaihyoKojo_3(String strUserId, String strKaishaCd)  throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strRetSql = new StringBuffer();

		String strBushoCd = userInfoData.getCd_busho();

		if(strKaishaCd.equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KAISHA"))
				&& strBushoCd.equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KOJO"))){
			// SQL���̍쐬
			strRetSql.append(" SELECT distinct ");
			strRetSql.append(" M101.cd_busho AS cd_busho,  ");
			strRetSql.append(" '��\�H��' AS nm_busho ");
			strRetSql.append(" FROM ma_user M101 ");
			strRetSql.append("  LEFT JOIN ma_busho M104 ");
			strRetSql.append("   ON M104.cd_busho = M101.cd_busho ");
			strRetSql.append("   AND M104.cd_kaisha = M101.cd_kaisha ");
			strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
			strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
			strRetSql.append("  AND M101.id_user =" + strUserId);
			strRetSql.append("  AND fg_hyoji = 1");
		}else{
			// SQL���̍쐬
			strRetSql.append(" SELECT distinct ");
			strRetSql.append(" M101.cd_busho AS cd_busho,  ");
			strRetSql.append(" ISNULL(M104.nm_busho,'') AS nm_busho ");
			strRetSql.append(" FROM ma_user M101 ");
			strRetSql.append("  LEFT JOIN ma_busho M104 ");
			strRetSql.append("   ON M104.cd_busho = M101.cd_busho ");
			strRetSql.append("   AND M104.cd_kaisha = M101.cd_kaisha ");
			strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
			strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
			strRetSql.append("  AND M101.id_user =" + strUserId);
			strRetSql.append("  AND fg_hyoji = 1");
		}
		return strRetSql;
	}
	/**
	 * �����u�S�āv�̕����f�[�^�擾�pSQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLAllBusho_DaihyoKojo_3(String strKaishaCd)  throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strRetSql = new StringBuffer();

		if(strKaishaCd.equals(ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KAISHA"))){
			strRetSql.append(" select");
			strRetSql.append(" distinct A.cd_busho");
			strRetSql.append(" ,A.nm_busho");
			strRetSql.append(" FROM");
			strRetSql.append(" (	SELECT ");
			strRetSql.append("	 distinct cd_busho, nm_busho");
			strRetSql.append("	FROM ma_busho");
			strRetSql.append("	 WHERE cd_kaisha =" + strKaishaCd +  " AND cd_busho <> " + ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KOJO") );
			strRetSql.append("     AND fg_hyoji = 1");
			strRetSql.append("	union all");
			strRetSql.append("	select");
			strRetSql.append("	 distinct cd_busho, '��\�H��' AS nm_busho");
			strRetSql.append("	from ma_busho ");
			strRetSql.append("	 WHERE cd_kaisha =" + strKaishaCd +  " AND cd_busho = " + ConstManager.getConstValue(Category.�ݒ�, "CD_DAIHYO_KOJO"));
			strRetSql.append("     AND fg_hyoji = 1");
			strRetSql.append(" ) AS A");
			strRetSql.append(" ORDER BY cd_busho");
		}
		else{
			// SQL���̍쐬
			strRetSql.append("SELECT distinct cd_busho, nm_busho");
			strRetSql.append(" FROM ma_busho");
			strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);
			strRetSql.append("  AND fg_hyoji = 1");
			strRetSql.append(" ORDER BY cd_busho");
		}
		return strRetSql;
	}
//mod end   -------------------------------------------------------------------------------

}
