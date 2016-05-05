package com.whty.common.page;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.whty.common.dao.SqlDao;

public class PageService {
    /**
     * logger
     */
    private Log logger = LogFactory.getLog(getClass());
    /**
     * 取得记录的sql语句id
     */
    private String recordSql;
    /**
     * 取得所有记录数量的sql语句id
     */
    private String countSql;

    private SqlDao sqlDao;
    
	public SqlDao getSqlDao() {
        return sqlDao;
    }

    public void setSqlDao(SqlDao sqlDao) {
        this.sqlDao = sqlDao;
    }

    public String getRecordSql() {
        return recordSql;
    }

    public void setRecordSql(String recordSql) {
        this.recordSql = recordSql;
    }

    public String getCountSql() {
        return countSql;
    }

    public void setCountSql(String countSql) {
        this.countSql = countSql;
    }

    @SuppressWarnings("unchecked")
	public PageList pageQuery(PageInfo pageInfo, Map params) throws Exception {
        PageList pageList = new PageList();

        // 统计记录数的SQL
        String countSql = getCountSql();
        // 查询数据的SQL
        String recordSql = getRecordSql();
        // 排序字段
        String fieldOrder = pageInfo.getFieldOrder();

        // 当前第几页
        Integer currentNumber = pageInfo.getCurrPage();
        if (currentNumber == null) {
            currentNumber = 1;
        }

        // 每页的最大记录数
        Integer pageSize = pageInfo.getPageSize();
        if (pageSize == null) {
            pageSize = 20;
        }

        int count, pageCount = 0;
        long l1 = System.currentTimeMillis();
        
        filterMap(params);
		
        if (countSql == null) {
            count = getSqlDao().count(recordSql, params, false);
        } else {
            count = getSqlDao().count(countSql, params, true);
            //防止执行其他recordSQL又没有设置countSQl时导致countSQl残留
            countSql = null;
        }
        long l2 = System.currentTimeMillis();
        logger.info("查询记录条数耗时："+(l2-l1));

        if (count > 0) {
            // 计算当前页码
            pageCount = (count % pageSize == 0) ? count / pageSize : count / pageSize + 1;

            // 修正当前页码
            if (currentNumber < 1) {
                currentNumber = 1;
            }
            if (currentNumber > pageCount) {
                currentNumber = pageCount;
            }

            // order by 子句
            String order = "";
            if (fieldOrder != null && !fieldOrder.trim().equals("")) {
                fieldOrder = fieldOrder.trim();
                if(fieldOrder.startsWith(",")){
                    fieldOrder = fieldOrder.substring(1);
                }
                if(fieldOrder.endsWith(",")){
                    fieldOrder = fieldOrder.substring(0,fieldOrder.length() - 1);
                }
                if(!fieldOrder.equals("")){
                    params.put("fieldSort", fieldOrder);
                }
            }

            // 获取数据的起始位置
            int skip = (currentNumber - 1) * pageSize;
            // 查询数据
            List list = getSqlDao().pageList(recordSql, params,skip,pageSize);
            pageList.setList(list);
            long l3 = System.currentTimeMillis();
            logger.info("查询记录详情耗时："+(l3-l2));

            String info = "";
            info += "总记录数：" + count;
            info += "\t页码：" + currentNumber + "/" + pageCount;
            info += "\t页大小：" + pageSize;
            info += "\t当前页记录数：" + list.size();
            logger.info(info);
        } else {
            currentNumber = 0;
        }

        params.remove("fieldSort");
        params.remove("head");
        params.remove("foot");

        pageInfo.setRecordCount(count);
        pageInfo.setTotalPage(pageCount);
        pageInfo.setCurrPage(currentNumber);
        pageInfo.setPageSize(pageSize);

        pageList.setPageInfo(pageInfo);

        return pageList;
    }
    
    /**
     * 将map中的空字符串替换为null
     * @param map
     */
    private void filterMap(Map<String, Object> map){
    	Iterator<Entry<String, Object>> it = map.entrySet().iterator();
    	while(it.hasNext()){
    		Entry<String, Object> en = it.next();
    		Object obj = en.getValue();
    		if(obj instanceof String && "".equals(obj.toString())){
    			en.setValue(null);
    		}
    	}
    }

    public PageList pageQuery(PageInfo pageInfo, String sql) throws Exception {
        PageList pageList = new PageList();

        // 查询数据的SQL
        String recordSql = "WMF.Common.select";
        // 排序字段
        String fieldOrder = pageInfo.getFieldOrder();

        // 当前第几页
        Integer currentNumber = pageInfo.getCurrPage();
        if (currentNumber == null) {
            currentNumber = 1;
        }

        // 每页的最大记录数
        Integer pageSize = pageInfo.getPageSize();
        if (pageSize == null) {
            pageSize = 20;
        }

        int count, pageCount = 0;
        count = getSqlDao().count("select count(*) from (" + sql + ") t");

        if (count > 0) {
            // 计算当前页码
            pageCount = (count % pageSize == 0) ? count / pageSize : count / pageSize + 1;

            // 修正当前页码
            if (currentNumber < 1) {
                currentNumber = 1;
            }
            if (currentNumber > pageCount) {
                currentNumber = pageCount;
            }

            // 获取数据的起始位置
            int skip = (currentNumber - 1) * pageSize;
            // 查询数据
            List list = getSqlDao().pageList(recordSql, sql,skip,pageSize);
            pageList.setList(list);

            String info = "";
            info += "总记录数：" + count;
            info += "\t页码：" + currentNumber + "/" + pageCount;
            info += "\t页大小：" + pageSize;
            info += "\t当前页记录数：" + list.size();
            logger.info(info);
        } else {
            currentNumber = 0;
        }

        pageInfo.setRecordCount(count);
        pageInfo.setTotalPage(pageCount);
        pageInfo.setCurrPage(currentNumber);
        pageInfo.setPageSize(pageSize);

        pageList.setPageInfo(pageInfo);

        return pageList;
    }
  

}
