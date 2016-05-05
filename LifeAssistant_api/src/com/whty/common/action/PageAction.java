package com.whty.common.action;

import com.whty.common.page.PageInfo;

/**
 * 所有需要分页的action的基类
 * @author hy
 * @date 2011-6-2
 */
public class PageAction extends BaseAction {
	public static  Integer  defaultPageSize=15;
	private static final long serialVersionUID = 365592760077282029L;
	
	/**
     * 每页显示记录数
     */
    protected Integer pageSize;
    /**
     * 当前页码
     */
    protected Integer currPage;
    /**
     * 总记录数
     */
    protected Integer recordCount;

    /**
     * 总页数
     */
    protected Integer totalPage;

    /**
     * 排序字符，单个的，不支持多个字段的排序 。etc. plan_name desc
     */
    protected String fieldOrder;
    /**
     * 在页面上显示的字段,Flexigrid专用
     */
    protected String fields;
    
    protected PageInfo wrapPageInfo(){
    	PageInfo pageInfo = new PageInfo();
    	pageInfo.setCurrPage(currPage);
    	pageInfo.setFieldOrder(fieldOrder);
    	pageInfo.setFields(fields);
    	pageInfo.setTotalPage(totalPage);
    	pageInfo.setPageSize(pageSize);
    	pageInfo.setRecordCount(recordCount);
    	return pageInfo;
    }
    
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Integer recordCount) {
		this.recordCount = recordCount;
	}
	public String getFieldOrder() {
		return fieldOrder;
	}
	public void setFieldOrder(String fieldOrder) {
		this.fieldOrder = fieldOrder;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}

	public Integer getCurrPage() {
		return currPage;
	}

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	
}
