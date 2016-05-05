package com.whty.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

/**
 * 数据库访问接口
 * <p>要支持各种数据库，只需要实现该接口即可。</p>
 *
 * @author hy
 * @version 1.0
 */
public interface SqlDao {
	
	/**
	 * 添加接口方法，便于GenerateSEQ接受来自业务逻辑层的dao类实例
	 * @author fanzhen 2012-2-16	
	 * @return
	 */
	 SqlMapClientImpl getSqlMapClientImpl();
	
    /**
     * 插入记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 插入对象
     * @return int 插入影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
     int create(String sqlID, Object object)
            throws DataAccessException;

    /**
     * 更新记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 更新对象
     * @return int 更新影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
     int update(String sqlID, Object object)
            throws DataAccessException;

    /**
     * 删除记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 删除对象
     * @return int 删除影响的记录数
     * @throws DataAccessException 数据库访问异常
     */
     int delete(String sqlID, Object object)
            throws DataAccessException;

    /**
     * 查询单个数据记录
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 查询参数
     * @return Object 单个数据对象
     * @throws DataAccessException 数据库访问异常
     */
     Object view(String sqlID, Object object)
            throws DataAccessException;

    /**
     * 查询数据列表
     *
     * @param sqlID   sqlmap文件sql语句对应的id
     * @param mapping 查询条件
     * @return List 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
     List list(String sqlID, Map mapping) throws DataAccessException;

    /**
     * 查询数据列表
     *
     * @param sqlID   sqlmap文件sql语句对应的id
     * @param mapping 查询条件
     * @param escape 是否对转义字符进行转义
     * @return List 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
     List list(String sqlID, Map mapping, boolean escape) throws DataAccessException;

    /**
     * 查询数据列表
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param object 查询参数
     * @return List 数据列表
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
     List list(String sqlID, Object object)
            throws DataAccessException;

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
     List pageList(String sqlID, Map object, int skipResults, int maxResults)
            throws DataAccessException;

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
     List pageList(String sqlID, String object, int skipResults, int maxResults)
            throws DataAccessException;

    /**
     * 查询记录数
     *
     * @param sqlID     sqlmap文件sql语句对应的id
     * @param params    查询条件
     * @param useConfig 是否使用配置的count sql来查询记录数
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
     int count(String sqlID, Map params, boolean useConfig)
            throws DataAccessException;

    /**
     * 查询记录数
     *
     * @param sqlID  sqlmap文件sql语句对应的id
     * @param params 查询条件
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     */
    @SuppressWarnings("unchecked")
     int count(String sqlID, Map params) throws DataAccessException;

    /**
     * 查询记录数
     *
     * @param sql  统计记录数的SQL语句
     * @return int 记录数
     * @throws DataAccessException 数据库访问异常
     */
     int count(String sql) throws DataAccessException;

    /**
     * 执行SQL语句
     * @param sql SQL语句
     * @throws DataAccessException
     */
     void execute(String sql) throws DataAccessException;

    /**
     * 执行一个查询SQL语句
     * @param sql SQL语句
     * @return MapBean的List
     * @throws DataAccessException
     */
    @SuppressWarnings("unchecked")
     List select(String sql) throws DataAccessException;
    
    
     int count(String sqlID, Object object) throws Exception ;
     
     /**
     * @param sqlID SQL语句ID
     * @param parameterObject  查询参数
     * @param keyProperty map的key对应的属性
     * @param valueProperty map的value对应的属性
     * @return
     */
    Map queryForMap(String sqlID,Object parameterObject,String keyProperty,String valueProperty);
    
}