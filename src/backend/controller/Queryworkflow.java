package backend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Pagination;
import backend.inputcheck.CheckQuery;

/**
 * Servlet implementation class Queryworkflow
 */
public class Queryworkflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryId=request.getParameter("queryid");
		String queryType=request.getParameter("querytype");
		String status=request.getParameter("status");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		String tense=request.getParameter("tense");

		
		String checked=CheckQuery.doCheckNull(queryType, status, year, month, tense);
		
		if(checked.equals("ok")) {
			 queryType= queryType.trim();
			 status=status.trim();
			 year=year.trim();
			 month= month.trim();
			 tense=tense.trim();
			 checked=CheckQuery.doMatch(year, month);
		}
		
		if(checked.equals("ok")) {
			
			int pageNumber;
			if (request.getParameter("pageNumber") == null) {
				pageNumber = 1;
			} else {
				pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
			}		
			
			String condition="";
			String cQueryType="";
			String cStatus="";
			String cCustom="";
			String cDate="'"+year+"-"+month+"-1"+"'";
			String cOrder="ORDER BY id DESC";
			
			if(queryId!=null) {
				if(!queryId.equals("")) {
					queryId=queryId.trim();
				}
			}
			
			if(!queryId.equals("")) {
				cQueryType="roleflow REGEXP '^[[:digit:]]+,)*" + queryId+ "$'"+" AND ";
			}
			
			if(queryType.equals("general")) {
				cCustom="custom = 'f'";
			}else {
				cCustom="custom = 't'";
			}
			
			if(!status.equals("3")) {
				cStatus=" AND "+"status="+status;
			}
	
			if(tense.equals("current")) {
				cDate= "YEAR(createtime)=" + year+ " and MONTH(createtime)=" + month;
			}else if(tense.equals("before")) {
				cDate="date(createtime)<="+cDate;
			}else if(tense.equals("after")) {
				cDate="date(createtime)>="+cDate;
			}
	
			condition=" WHERE "+cQueryType+cCustom+cStatus+" AND "+cDate+" "+cOrder;

			Pagination page = new Pagination(pageNumber, 2, "workflow",condition);

			if (page.getTotal() != 0) {
				String[] columns = { "id", "name", "account_id", "createtime",
						"status" };
				List<ArrayList<Object>> rows = page.getRows(columns);
				request.setAttribute("rows", rows);
				request.setAttribute("pageNumber", pageNumber);
				request.setAttribute("countPage", page.getCountPage());
				request.setAttribute("year", year);
				request.setAttribute("month", month);
				request.setAttribute("tense", tense);
				request.setAttribute("status", status);
				request.setAttribute("querytype", queryType);
				request.setAttribute("queryid", queryId);
			}	
		}
		
		System.out.println(checked);
		request.setAttribute("checked", checked);
		String url = "/WEB-INF/backend/queryworkflow.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
