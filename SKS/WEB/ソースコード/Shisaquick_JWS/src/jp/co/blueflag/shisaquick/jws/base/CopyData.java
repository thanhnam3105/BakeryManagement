package jp.co.blueflag.shisaquick.jws.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.manager.TrialTblData;

/**
 * 
 * �R�s�[�f�[�^�ێ�
 *  : �R�s�[�f�[�^�̊Ǘ����s��
 *
 */
public class CopyData extends DataBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 855764011683433197L;
	private ArrayList aryCopyData;
	private boolean blnCopyFg;
	
	/**
	 * �R���X�g���N�^
	 */
	public CopyData(){
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
	}

}
