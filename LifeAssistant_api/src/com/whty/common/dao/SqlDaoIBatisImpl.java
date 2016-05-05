package com.whty.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMapping;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.scope.SessionScope;
import com.ibatis.sqlmap.engine.scope.StatementScope;


/**
 * 数据库访问对象的iBatis实现
 * <p>该类是一个公用的数据库操作类，他提供了数据库访问的CRUD等基本方法和分页需要用到的特殊方法</p>
 * <p>在service层通过注入该对象，就可以实现对数据库的访问。</p>
 *
 * @author hy
 * @version 1.0
 */
public class SqlDaoIBatisImpl extends SqlMapClientTemplate implements SqlDao {
    /**
     * logger
     */
    private Log logger = LogFactory.getLog(getClass());

    /**
     * 获取SqlMapClient实现类
     *
     * @return SqlMapClientImpl
     */
    public SqlMapClientImpl getSqlMapClientImpl() {
        return (SqlMapClientImpl) super.getSqlMapClient();
    }

    /**
     * 插入记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 插入对象
     * @return int 插入影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int create(String sqlID, Object object) throws DataAccessException {
        sqlPrint(sqlID, object);
        int ret = super.update(sqlID, object);
        logger.info("新增了" + ret + "条记录");

        return ret;
    }

    /**
     * 更新记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 更新对象
     * @return int 更新影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int update(String sqlID, Object object) throws DataAccessException {
        sqlPrint(sqlID, object);
        int ret = super.update(sqlID, object);
        logger.info("修改了" + ret + "条记录");

        return ret;
    }

    /**
     * 删除记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 删除对象
     * @return int 删除影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int delete(String sqlID, Object object) throws DataAccessException {
        sqlPrint(sqlID, object);
        int ret = super.delete(sqlID, object);
        logger.info("删除了" + ret + "条记录");

        return ret;
    }

    /**
     * 查询单个记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 查询参数
     * @return Object 单个记录对象
     * @throws DataAccessException 数据库访问异常
     */
    public Object view(String sqlID, Object object) throws DataAccessException {
        sqlPrint(sqlID, object);
        return super.queryForObject(sqlID, object);
    }

    /**
     * 查询数据列表
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param params 查询条件
     * @return List   数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List list(String sqlID, Map params) throws DataAccessException {
        Map parameter = prepareParameter(params);
        sqlPrint(sqlID, parameter);
        return super.queryForList(sqlID, parameter);
    }

    /**
     * 查询数据列表
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param params 查询条件
     * @param escape 是否对转义字符进行转义
     * @return List   数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List list(String sqlID, Map params, boolean escape) throws DataAccessException {
        Map parameter = params;
        if(escape) {
            parameter = prepareParameter(params);
        }
        sqlPrint(sqlID, parameter);
        return super.queryForList(sqlID, parameter);
    }

    /**
     * 查询数据列表
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 查询条件对象
     * @return List 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List list(String sqlID, Object object) throws DataAccessException {
        sqlPrint(sqlID, object);
        return super.queryForList(sqlID, object);
    }

    /**
     * 查询某一页数据
     * @param sqlID sqlmap文件sql语句对应的id
     * @param object 查询条件对象
     * @param skipResults 从第几条数据开始取数据
     * @param maxResults 最多取多少条数据
     * @return 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List pageList(String sqlID, Map object, int skipResults, int maxResults) throws DataAccessException {
        sqlPrint(sqlID, object);
        return super.queryForList(sqlID, object,skipResults,maxResults);
    }

    /**
     * 查询某一页数据
     * @param sqlID sqlmap文件sql语句对应的id
     * @param object 查询SQL语句
     * @param skipResults 从第几条数据开始取数据
     * @param maxResults 最多取多少条数据
     * @return 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public List pageList(String sqlID, String object, int skipResults, int maxResults) throws DataAccessException {
        sqlPrint(sqlID, object);
        return super.queryForList(sqlID, object,skipResults,maxResults);
    }

    /**
     * 查询记录数
     *
     * @param sqlID     sqlmap文件sql语句对应的id
     * @param params    查询条件
     * @param useConfig 是否使用sqlmap中配置的count sql来查询记录数，如果不是，则该方法自动包装select count语句
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public int count(String sqlID, Map params, boolean useConfig) throws DataAccessException {
        Map parameter = prepareParameter(params);
        // 如果使用sqlmap中配置的sql查询记录数，则直接查询
        if (useConfig) {
            sqlPrint(sqlID, parameter);
            return (Integer) queryForObject(sqlID, parameter);
        } else { // 否则包装一个查询记录SQL语句
            // 拼接统计记录数的SQL
            SqlMapClientImpl sqlMapClient = getSqlMapClientImpl();
            MappedStatement statement = sqlMapClient.getMappedStatement(sqlID);
            StatementScope scope = new StatementScope(new SessionScope());
            statement.initRequest(scope);
            Sql sql = statement.getSql();
            String countSql = sql.getSql(scope, parameter);
            countSql = countSql == null ? countSql : countSql.trim();
            countSql = "select count(*) from (" + countSql + ") t";
            // 获取输入参数
            ParameterMap parameterMap = sql.getParameterMap(scope, parameter);
            Object[] objects = parameterMap.getParameterObjectValues(scope, parameter);

            // 查询记录数
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet result = null;
            int count = 0;
            try {
                connection = DataSourceUtils.getConnection(this.getDataSource());
                preparedStatement = connection.prepareStatement(countSql);
                parameterMap.setParameters(scope, preparedStatement, objects);
                result = preparedStatement.executeQuery();
                while (result.next()) {
                    count = result.getInt(1);
                }

                if (logger.isInfoEnabled()) {
                    String str = "";
                    if (objects != null) {
                        for (int i = 0; i < objects.length; i++) {
                            str += "参数" + (i + 1) + ":" + (objects[i] == null ? "null" : objects[i].toString()) + "\n";
                        }
                    }
                    logger.info(sqlID + ": " + countSql + "\n返回结果：" + count + "\n参数列表：\n" + str);
                }

            } catch (SQLException e) {
                throw getExceptionTranslator().translate("JDBC operation", null, e);
            } finally {
                try {
                    if (result != null) {
                        result.close();
                    }
                } catch (SQLException e) {
                    // ignore
                }
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    // ignore
                }
                if (connection != null) {
                    DataSourceUtils.releaseConnection(connection, this.getDataSource());
                }
            }

            return count;
        }
    }

    /**
     * 查询记录数
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param params 查询条件
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
    public int count(String sqlID, Map params) throws DataAccessException {
        Map parameter = prepareParameter(params);
        return count(sqlID, parameter, true);
    }

    /**
     * 查询记录数
     *
     * @param sql  统计记录数的SQL语句
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     */
    public int count(String sql) throws DataAccessException {
        return (Integer) super.queryForObject("whty.Common.queryCount", sql);
    }
    
    
    public int count(String sqlID, Object object) throws Exception 
    {
    	 return (Integer) super.queryForObject(sqlID, object);
    }
    /**
     * 执行SQL语句
     * @param sql SQL语句
     * @throws DataAccessException
     */
    public void execute(String sql) throws DataAccessException {
        super.update("whty.Common.execute",sql);
    }

    /**
     * 打印sql语句
     *
     * @param sqlID     sqlmap文件sql语句对应的id
     * @param parameter 参数对象
     */
    private void sqlPrint(String sqlID, Object parameter) {
        if (logger.isInfoEnabled()) {
            SqlMapClientImpl sqlMapClient = getSqlMapClientImpl();
            MappedStatement statement = sqlMapClient.getMappedStatement(sqlID);
            StatementScope scope = new StatementScope(new SessionScope());
            statement.initRequest(scope);
            Sql sql = statement.getSql();
            String strSql = sql.getSql(scope, parameter);
            ParameterMap parameterMap = sql.getParameterMap(scope, parameter);

            // 参数名称
            ParameterMapping[] mapping_array = parameterMap.getParameterMappings();
            String[] names = new String[mapping_array.length];
            for (int i = 0; i < mapping_array.length; i++) {
                ParameterMapping mapping = mapping_array[i];
                String propertyName = mapping.getPropertyName();
                names[i] = propertyName;
            }

            // 参数值
            Object[] objects = parameterMap.getParameterObjectValues(scope, parameter);
            String param = "";
            if (objects != null) {
                for (int i = 0; i < objects.length; i++) {
                    param += "参数" + (i + 1) + "(" + names[i] + "):" + (objects[i] == null ? "null" : objects[i].toString()) + "\n";
                }
            }
            logger.info(sqlID + ": " + strSql + "\n参数列表：\n" + param);
        }
    }

    /**
     * 预处理参数
     * 1、将值为单撇号的转移成
     *
     * @param parameter 参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map prepareParameter(Map parameter) {
        Map<String, Object> newMap = new HashMap<String, Object>();
        try {
            if (parameter != null) {
                newMap.putAll(parameter);
                Set set = newMap.keySet();
                for (Iterator iter = set.iterator(); iter.hasNext();) {
                    String key = (String) iter.next();
                    Object obj = newMap.get(key);
                    if (obj instanceof String) { // 如果值对象是字符串型
                        if (obj.equals("")) {
                            iter.remove(); // 注意：不要用newMap.remove(key)，会导致ConcurrentModificationException
                        } else {
                            String value = (String) obj;
                            value = value.replace("'", "''"); // 替换单撇号'
                            newMap.put(key, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.info(e);
        }

		return newMap;
	}

    @SuppressWarnings("unchecked")
    public List select(String sql) throws DataAccessException {
        return super.queryForList("whty.Common.select", sql);
    }
}
