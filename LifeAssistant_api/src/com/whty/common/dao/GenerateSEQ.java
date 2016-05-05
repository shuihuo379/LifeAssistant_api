package com.whty.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

/**
 * 获取序列公用类<br>
 * 每个序列计数保存在系统 配置表中。当每取一次序列计数后并立即修改计数器增1<br>
 * 如果系统 配置表所配置序列计数器有误，则向外抛获取xx序列失常，并检查系统配置xx项<br>
 * 使用构函时需要传参业务逻辑层的dao操作类实例<br>
 * 
 * @author fanzhen
 * @date 2012-02-16
 */
public class GenerateSEQ {

	// 借助业务逻辑层的dao操作类实例
	private SqlDao sqlDao;

	public SqlDao getSqlDao() {
		return sqlDao;
	}

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	public GenerateSEQ(SqlDao sqlDao) {
		this.setSqlDao(sqlDao);
	}
	 private Log logger = LogFactory.getLog(getClass());
	/**
	 * 根据传参，获取指定的序列计数器
	 * 
	 * @author fanzhen 2012-2-16
	 * @param seqName
	 * @return
	 * @throws Exception
	 */
	public String getNextSEQ(String seqName) throws Exception {
		if (seqName == null || seqName.trim().equals("")) {
			throw new Exception("获取序列失败，请检查传参序列名。");
		}
		String nextSEQ = "";

		// 组装查询计数器 sql
		//String querySQL = " select PKID,PARAM_CODE,PARAM_VALUE from ST_PARAM ";
		//querySQL += "  where VALID_FLAG = '1' and PARAM_CODE = '" + seqName + "' ";
        StringBuffer querySQL=new StringBuffer(" select PKID,PARAM_CODE,PARAM_VALUE from ST_PARAM ");
        querySQL.append("  where VALID_FLAG = '1' and PARAM_CODE = '" + seqName + "' ");
		// 组装修改递增计数器sql
		//String updateSQL = " update ST_PARAM set PARAM_VALUE = '{0}' ";
		//updateSQL += " where PARAM_CODE = '" + seqName + "' ";
        StringBuffer updateSQL=new StringBuffer(" update ST_PARAM set PARAM_VALUE = '{0}' ");
        updateSQL.append(" where PARAM_CODE = '" + seqName + "' ");
		// dao操作相关类
		SqlMapClientImpl sqlMapClient = this.getSqlDao().getSqlMapClientImpl();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		// 开始一段事务
		try {
			sqlMapClient.startTransaction();

			connection = sqlMapClient.getCurrentConnection();
			preparedStatement = connection.prepareStatement(querySQL.toString());
			result = preparedStatement.executeQuery();

			// 获取当前计数
			while (result.next()) {
				nextSEQ = result.getString(3);
			}
			if (nextSEQ == null || nextSEQ.trim().equals("")) {
				throw new Exception("获取序列失败，请检查序列初始值配置。");
			}
			
			// 检查计数数值是否为int型
			int iCount = 0;
			try {
				iCount = Integer.parseInt(nextSEQ);
				iCount++;
			} catch (Exception e) {
				throw new Exception("获取序列失败，请检查序列初始值配置。"+e);
				
			}

			// 递增计数器
			preparedStatement = connection.prepareStatement(updateSQL.toString().replace("{0}", String.valueOf(iCount)));
			preparedStatement.execute();
			
			sqlMapClient.commitTransaction();
		} catch (Exception e) {
			//e.printStackTrace();
			logger.info(e.getMessage(),e);
			sqlMapClient.endTransaction();
			throw new Exception("获取序列时事务出现异常。" + e);
		} finally {
			sqlMapClient.endTransaction();
			
		}

		return nextSEQ;
	}
}
