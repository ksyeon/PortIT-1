package portit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portit.model.dao.MemberDao;
import portit.model.dao.ViewDao;
import portit.model.dto.Member;


@WebServlet("/login")
public class LogoController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		
		Member member = new Member();
		member.setMem_email((String)req.getParameter("userid"));
		member.setMem_password((String)req.getParameter("userpw"));
		
		MemberDao memberDao = new MemberDao();
		Member checkMem = memberDao.search(member); // 검사 용도
		
		
		if(member.getMem_email().equals(checkMem.getMem_email()) && member.getMem_password().equals(checkMem.getMem_password())) {
			// 세션에 로그인 정보 넣기
			HttpSession session = req.getSession(true);
			session.setAttribute("loginId", checkMem.getMem_id());
			session.setAttribute("loginEmail", checkMem.getMem_email());
			session.setAttribute("loginPw", checkMem.getMem_password());
			
			int loginId = (int)session.getAttribute("loginId");
			
			String url = null;
			String cmd = req.getParameter("cmd");
			
			//dao 호출
			ViewDao viewDao = new ViewDao();
					
			List port_list = viewDao.portfolio_info();
			List mem_list = viewDao.member_info();
			List proj_list = viewDao.project_info();
			List time_list = viewDao.timeline_info(loginId);
		
			//Model에서 가지고 온 정보를 View에 넘겨주기 위해 변수 선언
			req.setAttribute("port_list", port_list);
			req.setAttribute("mem_list", mem_list);
			req.setAttribute("proj_list", proj_list);			
			req.setAttribute("time_list", time_list);	
			
			if(cmd.equals("MAIN")){
				//out.println("<script>location.href='/page?page=main';</script>");
				url="/page?page=main";
				
				RequestDispatcher view = req.getRequestDispatcher(url);
				view.forward(req,resp);
			}
		}
		else {
			out.println("<script>alert('이메일 또는 비밀번호가 틀렸습니다.'); location.href='/index.jsp';</script>");			
		}	
	}
}