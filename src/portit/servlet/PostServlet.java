package portit.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import portit.controller.FileUploadController;
import portit.controller.PortfolioAddController;


/**
 * 게시물 작성 서블릿
 *
 */
@SuppressWarnings("serial")
@WebServlet("/post")
public class PostServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// GET 요청일 경우 이동
		resp.sendRedirect("/page?page=main");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// POST 요청일 때 등록 처리
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		if (!ServletFileUpload.isMultipartContent(req)) {
			try {
				throw new Exception("요청이 multipart/form-data로 인코딩되지 않았습니다.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		FileUploadController uploadController = new FileUploadController();
		uploadController.fileUpload(req, resp);
		req.setAttribute("formData", req.getAttribute("formData"));
		req.setAttribute("fileNames", req.getAttribute("fileNames"));
		
		String viewUrl = "";
		String articleType = req.getParameter("type");
		if ("profile".equals(articleType)) {
			ProfileAddController profileAddController = new ProfileAddController();
			viewUrl = profileAddController.execute(req, resp);
		} else if ("portfolio".equals(articleType)) {
			PortfolioAddController portfolioAddController = new PortfolioAddController();
			viewUrl = portfolioAddController.execute(req, resp);
		} else if ("project".equals(articleType)) {
			ProjectAddController projectAddController = new ProjectAddController();
			viewUrl = projectAddController.execute(req, resp);
		}
		System.out.println("viewUrl: " + viewUrl.substring(0, 3) + "/" + viewUrl.substring(4));
		RequestDispatcher rd = null;
		if (viewUrl.substring(0, 3).equals("inc")) {
			rd = req.getRequestDispatcher(viewUrl.substring(4));
			rd.include(req, resp);
		} else if (viewUrl.substring(0, 3).equals("fwd")) {
			rd = req.getRequestDispatcher(viewUrl.substring(4));
			rd.forward(req, resp);
		} else {
			resp.sendRedirect(viewUrl.substring(4));
		}
	}

}
