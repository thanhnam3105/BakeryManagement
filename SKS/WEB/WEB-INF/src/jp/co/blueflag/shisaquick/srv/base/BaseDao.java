package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BaseDao extends ObjectBase {

	//セッションファクトリー
	protected SessionFactory sessionFactory = null;
	//セッションファクトリー(DB1)
	static protected SessionFactory sessionFactory1 = null;
	//セッションファクトリー(DB1)
	static protected SessionFactory sessionFactory2 = null;
	//セッションファクトリー(DB1)
	static protected SessionFactory sessionFactory3 = null;

	/**
	 * dbのカテゴリ 
	 */
	public static enum DBCategory{
		DB1,
		DB2,
		DB3
	}

	public BaseDao(DBCategory DB_Category){
		
		if(DB_Category == DBCategory.DB1){

			if (sessionFactory1 == null){
			    sessionFactory1 = new Configuration()
		        .configure(ConstManager.CONST_XML_PATH_DB1)
		        .buildSessionFactory();
				
			}
			sessionFactory = sessionFactory1;
			
		}else if(DB_Category == DBCategory.DB2){
			
			if (sessionFactory2 == null){
			    sessionFactory2 = new Configuration()
		        .configure(ConstManager.CONST_XML_PATH_DB2)
		        .buildSessionFactory();
				
			}
			sessionFactory = sessionFactory2;
			
		}else if(DB_Category == DBCategory.DB3){
			
			if (sessionFactory3 == null){
			    sessionFactory3 = new Configuration()
		        .configure(ConstManager.CONST_XML_PATH_DB3)
		        .buildSessionFactory();
				
			}
			sessionFactory = sessionFactory3;
			
		}
		
	}
	
}
