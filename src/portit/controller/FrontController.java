package portit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD
/**
 * 프론트 컨트롤러
 * 클라이언트의 서블릿 요청을 받아 기능을 해당 컨트롤러에게 분배하고 해당 컨트롤러의 처리 결과를 뷰에게 전달
 * @author gnsngck
 *
 */

@SuppressWarnings("serial")
<<<<<<< HEAD
@WebServlet("*.do") /* HTML/JSP에서 .do로 끝나는 요청은 모두 서블릿 요청으로 간주하여 프론트 컨트롤러가 처리 */
=======
>>>>>>> refs/remotes/origin/dev-back-MsgByBG22
public class FrontController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
<<<<<<< HEAD
=======
		System.out.println("프론트 Ctrl");
>>>>>>> refs/remotes/origin/dev-back-MsgByBG22
		resp.setContentType("text/html; charset=UTF-8");

		// 팩토리에 의한 인스턴스 생성
		ControllerFactory factory = ControllerFactory.getInstance();
		Controller controller = factory.createController(req.getServletPath());
		
		RequestDispatcher rd = null;
		try {
			// 인터페이스를 따라 뷰 주소를 반환하는 구현된 컨트롤러의 실행 메소드 호출
			String[] viewUrl = controller.execute(req, resp);
			
			// 담당 컨트롤러가 반환하는 뷰의 URL을 이용하여 뷰에게 실행을 위임
			if (viewUrl[0].equals("inc:")) {
				// 인클루딩
				rd = req.getRequestDispatcher(viewUrl[1]);
				rd.include(req, resp);
			} else if (viewUrl[0].equals("fwd:")) {
				// 포워딩
				rd = req.getRequestDispatcher(viewUrl[1]);
				rd.forward(req, resp);
			} else {
				// 리다이렉트
				rd = req.getRequestDispatcher(viewUrl[1]);
				rd.forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 에러를 세션에 저장
			req.setAttribute("error", e);
			// 에러페이지로
			rd = req.getRequestDispatcher("");
			rd.forward(req, resp);
		}
	}

}
*/
=======
import portit.model.dto.Member;

/**
 * 프론트 컨트롤러
 * 
 * @author gnsngck
 *
 */
@SuppressWarnings("serial")
public class FrontController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("프론트 Ctrl");
		resp.setContentType("text/html; charset=UTF-8");

		// 클라이언트가 요청한 주소 알아내기
		String requestPath = req.getServletPath();
		try {

			String controllerPath = null;
			if ("/join".equals(requestPath)) {
				controllerPath = "/join";
				// 회원가입 정보를 저장
				req.setAttribute("member", new Member()
						.setMem_email(req.getParameter("email"))
						.setMem_password(req.getParameter("password"))
						);
			}

			// 인클루드하여 처리 위임
			RequestDispatcher rd = req.getRequestDispatcher(controllerPath);
			rd.include(req, resp);

			String viewURL = (String) req.getAttribute("viewURL");
			rd = req.getRequestDispatcher(viewURL);
			rd.include(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
			// 에러를 세션에 저장
			req.setAttribute("error", e);
			// 에러페이지로
			RequestDispatcher rd = req.getRequestDispatcher("");
			rd.forward(req, resp);
		}
	}

}
>>>>>>> refs/remotes/origin/Insu2
