package myfilter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rbac.javabean.RbacAccount;

/**
 * Servlet Filter implementation class BackendFilter
 */
public class BackendCheckFilter implements Filter {

	private FilterConfig filterConfig = null;
	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		ServletContext sc = filterConfig.getServletContext();
		HashMap<Integer,RbacAccount> rbac=(HashMap<Integer,RbacAccount>)sc.getAttribute("rbac");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		String path = httpRequest.getContextPath();
		String basePath = httpRequest.getScheme()+"://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+path+"/";
		
		if(session.getAttribute("id")==null) {
			httpResponse.sendRedirect(basePath+"index.jsp");
		}else {
			int accountid=(Integer)httpRequest.getSession().getAttribute("id");
			System.out.println(rbac.size());
			if(rbac.get(accountid).getRole().contains(1000)) {
				chain.doFilter(request, response);
			}else {
				httpResponse.sendRedirect(basePath+"index.jsp");
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig=fConfig;
	}

}
