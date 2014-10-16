package myfilter;

import java.io.IOException;
import java.util.ArrayList;
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
		HashMap<Integer,ArrayList<String>> ControllerActions=(HashMap<Integer,ArrayList<String>>)sc.getAttribute("actions");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		
		int id=0;
		if(session.getAttribute("id") != null ) {
			id=(Integer)session.getAttribute("id");
		}
		
		//RBAC请求判断
		boolean checked=false;
		String URI=httpRequest.getRequestURI();
		String requestAction = URI.substring(URI.indexOf("/", 1)+1);
		ArrayList<Integer> roles=rbac.get(id).getRole();
		for(int roleId : roles) {
			ArrayList<String> actions=ControllerActions.get(roleId);
			for(String action : actions) {
				if(action.equals(requestAction))
				{ checked= true ;
					break;
				}
			}
			if(checked == true) break;
		}
		
		if(id == 0 || checked == false) {
			String path = httpRequest.getContextPath();
			String basePath = httpRequest.getScheme()+"://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+path+"/";
			httpResponse.sendRedirect(basePath+"error.jsp");
		}else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.filterConfig=fConfig;
	}

}
