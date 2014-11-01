package frontend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rbac.javabean.RbacAccount;
import rbac.javabean.RbacRole;
import frontend.dao.D_Workflow;
import frontend.inputcheck.CheckWorkflow;
import backend.dao.D_Defaultflow;
import backend.dao.D_Workform;
import backend.javabean.Defaultflow;
import backend.javabean.Workform;

/**
 * Servlet implementation class Nworkflow
 */
public class Nworkflow extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String formid = request.getParameter("formid");
		Workform workform = D_Workform.doSelect(formid);
		ArrayList<Defaultflow> workflows = D_Defaultflow.doSelectActive(formid);
		HashMap<Integer, RbacAccount> rbac = (HashMap<Integer, RbacAccount>) getServletContext().getAttribute("rbac");
		HashMap<Integer, RbacRole> roles = (HashMap<Integer, RbacRole>) getServletContext().getAttribute("roles");

		String workformName = request.getParameter("workformName");
		String mWorkflow = request.getParameter("mWorkflow");
		String content = request.getParameter("editor1");
		int accountid = (Integer) request.getSession().getAttribute("id");

		String checked = CheckWorkflow.doCheckNull(mWorkflow, workformName,content);

		if (checked.equals("ok")) {
			mWorkflow = mWorkflow.trim();
			workformName = workformName.trim();
			content = content.trim();

			checked = CheckWorkflow.doMatch(mWorkflow, workformName);
		}

		String roleflow;
		String custom;
		if (checked.equals("ok")) {

			String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			String kaptchaReceived = request.getParameter("kaptcha");

			if (kaptchaReceived == null || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
					checked="验证码错误";
			} else {

				if (mWorkflow.substring(0, 1).equals("0")) {
					roleflow = mWorkflow.substring(2);
					custom = "t";
				} else {
					//3，4，6，7  我有 6 只需 7 签，类推
					String workflow[] = mWorkflow.split(",");
					roleflow = workflow[workflow.length - 1];
					for (int i = workflow.length - 2; i >= 0; i--) {
						// 判断是否有此角色
						if (rbac.get(accountid).getRole().contains(Integer.valueOf( workflow[i]))) {
							break;
						}
						roleflow = workflow[i] + "," + roleflow;
					}

					custom = "f";
				}

				int count = D_Workflow.doCreate(workformName, roleflow,content, accountid, custom);
				if (count != 0) {
					response.sendRedirect("nworkflow?checked=success&formid="+ formid);
					return;
				} else {
					checked = "数据库操作失败";
				}
			}

		}

		request.setAttribute("formid", formid);
		request.setAttribute("workform", workform);
		request.setAttribute("workflows", workflows);
		request.setAttribute("checked", checked);

		String url = "/WEB-INF/frontend/nworkflow.jsp";
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
