package tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionPool;
import database.DBUtil;


public class Pagination {

	private String tableName;
	
	private Integer nowPage=1;

	private Integer pageSize=20;

	private Integer countPage;

	private Integer total;

	private Integer startIndex;

	private Integer endIndex;
	
	private String where;

	public Pagination(Integer nowPage, Integer pageSize, String tableName,String where) {
		
		this.nowPage = nowPage;
		this.pageSize = pageSize;
		this.tableName=tableName;
		this.where=where;

		
		if (this.nowPage < 1) {
			this.nowPage = 1;
		}

		this.total = this.getCountSize();

		this.countPage = this.total % this.pageSize == 0 ? this.total
				/ this.pageSize : this.total / this.pageSize + 1;

		if (this.nowPage > this.countPage) {
			this.nowPage = this.countPage;
		}

		this.startIndex = (this.nowPage - 1) * this.pageSize;
		this.endIndex = this.pageSize;

	}

	public Integer getCountSize() {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    String query = "SELECT COUNT(*) AS c FROM " + tableName + where;
	    
	    //查看 sql
	    //System.out.println(query);
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	rs=ps.executeQuery();
	    	if(rs.next()) {
	    		return rs.getInt("c");
	    	}
	    	return 0;
	    }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	        return 0;
	    }
	    finally
	    {	DBUtil.closeResultSet(rs);
	        DBUtil.closePreparedStatement(ps);
	        pool.freeConnection(connection);
	    }
	}

	public List<ArrayList<Object>> getRows(String[] columns) {
		ConnectionPool pool = ConnectionPool.getInstance();
	    Connection connection = pool.getConnection();
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    
	    StringBuffer str = new StringBuffer();
	    for(int i = 0; i < columns.length; i++){
	    	if(i+1 < columns.length) {
	    		str. append(columns[i]+',');
	    	}else {
	    		str. append(columns[i]);
	    	}
	    }
    
	    String selectColumns = str.toString();
	    
	    String query = "SELECT " + selectColumns + " FROM " + tableName + where + " LIMIT ?,?";
	    
	    try {
	    	ps = connection.prepareStatement(query);
	    	ps.setInt(1, this.startIndex);
	    	ps.setInt(2, this.endIndex);
	    	rs=ps.executeQuery();
	    	List<ArrayList<Object>> rows=new ArrayList<ArrayList<Object>>();
	    	ArrayList<Object> row;
	    	while(rs.next()) {
	    		row=new ArrayList<Object>();
	    		for(String column : columns) {
	    			row.add(rs.getObject(column));
	    		}
	    		rows.add(row);
	    	}
	    	return rows;
	    }
	    catch(SQLException e)
	    {
	        e.printStackTrace();
	        return null;
	    }
	    finally
	    {	DBUtil.closeResultSet(rs);
	        DBUtil.closePreparedStatement(ps);
	        pool.freeConnection(connection);
	    }

	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public Integer getTotal() {
		return total;
	}
	
	public Integer getCountPage() {
		return countPage;
	}

}

