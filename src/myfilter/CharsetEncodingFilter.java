package myfilter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class CharsetEncodingFilter
 */
public class CharsetEncodingFilter implements Filter {

	   private String endcoding;
	    

	    @Override
	    public void destroy() {
	    }
	    
	    @Override
	    public void doFilter(ServletRequest request, ServletResponse response,
	            FilterChain chain) throws IOException, ServletException {
	        
	        //System.out.println("CharsetEncodingFilter--->>>begin");
	        

	        request.setCharacterEncoding(endcoding);
	        
	        //System.out.println("CharsetEncodingFilter--->>>doing");
	        
	        chain.doFilter(request, response);
	        
	        //System.out.println("CharsetEncodingFilter--->>>end");
	    }
	    
	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {
	        this.endcoding = filterConfig.getInitParameter("endcoding");
	        //System.out.println("CharsetEncodingFilter.init()-->> endcoding=" + endcoding);
	    }

}
