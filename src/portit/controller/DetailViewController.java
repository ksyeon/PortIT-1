package portit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import portit.model.dao.SearchDao;
import portit.model.dao.ViewDao;
import portit.model.dto.Portfolio;

@WebServlet("/detailView")
public class DetailViewController extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		

		System.out.println("0.서블릿접근!");
		
		String yame_string = (String) req.getParameter("yame");
		
		int yame=0;
		if(yame_string!=null){
		yame=Integer.parseInt(yame_string);
		System.out.println("서블릿:파라미터 수신"+yame);
		}
		
		String yame_pj_string = (String) req.getParameter("yame_pj");
		int yame_pj=0;
		if(yame_pj_string!=null){
			System.out.println("서블릿:파라미터 수신2"+yame_pj_string);
		 yame_pj=Integer.parseInt(yame_pj_string);
		}

	
		
		String cmd = req.getParameter("cmd");
		String url = null;
		SearchDao searchDao = new SearchDao();		
		ViewDao viewdao=new ViewDao();
		
	
		//페이크  프로필 페이지.
		
		if(yame==2){
			
			System.out.println("fake_profile"+yame);
			url="profile_sample1.html";
		}else if(yame==7){//상민
			
			url="profile_sample3.html";
		}else if(yame==9){//권상우
			
			url="profile_sample2.html";
		}
		
		//야매  프로젝트페이지
		if(yame_pj==3){		//파리의연인
			
			System.out.println("fake_PJ"+yame);
			url="projDetail_sample1.html";
		}else if(yame_pj==10){	//태양의후예
			
			url="projDetail_sample2.html";
		}else if(yame_pj==4){	//도깨비
			url="projDetail_sample3.html";
		}
		
		
		
		
		
		
		
		
		if("PORTFOLIO".equals(cmd)){
			

			System.out.println(" 서블릿접근!CMD"+cmd);
			
			
			int pf_id = Integer.parseInt(req.getParameter("pf_id"));
			
			
			List list = viewdao.portfolio_info(pf_id);
			
			Portfolio portfolio =(Portfolio)list.get(0);
			System.out.println("데이터 수신확인"+portfolio.getPf_title());
			
			System.out.println("CTRL 포폴 접근요청"+cmd+pf_id);
			req.setAttribute("pf_view", viewdao.portfolio_info(pf_id));	//이름만 바꿔주면 됨.
			
			
			
			
			url="/page?page=searchAll";
		}
		
		/*
		
		else if(cmd.equals("MEMBER")){
			//int prof_id = Integer.parseInt(req.getParameter("prof_id"));
			//req.setAttribute("prof_view",viewdao.member_info(prof_id));
			url="/page?page=";
		}
		else if(cmd.equals("PROJECT")){
			//int proj_id = Integer.parseInt(req.getParameter("proj_id"));
			//req.setAttribute("proj_view",viewdao.project_info(proj_id));			
			url="/page?page=";
		}
		else if(cmd.equals("TAG")){
			String tag_name = req.getParameter("tag_name");
			System.out.println(tag_name);
			tag_name = tag_name.toUpperCase();
			List port_list = searchDao.searchAll_port(tag_name,true);
			List mem_list = searchDao.searchAll_member(tag_name,true);
			List proj_list = searchDao.searchAll_proj(tag_name,true);	
		
			//Model에서 가지고 온 정보를 View에 넘겨주기 위해 변수 선언
			req.setAttribute("port_list", port_list);
			req.setAttribute("mem_list", mem_list);
			req.setAttribute("proj_list", proj_list);
						
			url="/page?page=searchAll";
		}
		else if(cmd.equals("PFTAG")){
			String tag_name = req.getParameter("tag_name");
			System.out.println(tag_name);
			tag_name = tag_name.toUpperCase();
			List port_list = searchDao.searchAll_port(tag_name,true);
			
			//Model에서 가지고 온 정보를 View에 넘겨주기 위해 변수 선언
			req.setAttribute("port_list", port_list);
			
			url="/page?page=pfSearch";
		}
		else if(cmd.equals("MEMTAG")){
			String tag_name = req.getParameter("tag_name");
			System.out.println(tag_name);
			tag_name = tag_name.toUpperCase();
			List mem_list = searchDao.searchAll_member(tag_name,true);
			
			//Model에서 가지고 온 정보를 View에 넘겨주기 위해 변수 선언
			req.setAttribute("mem_list", mem_list);
			
			url="/page?page=memSearch";
		}
		else if(cmd.equals("PROJTAG")){
			String tag_name = req.getParameter("tag_name");
			System.out.println(tag_name);
			tag_name = tag_name.toUpperCase();
			List proj_list = searchDao.searchAll_proj(tag_name,true);	
			
			//Model에서 가지고 온 정보를 View에 넘겨주기 위해 변수 선언
			req.setAttribute("proj_list", proj_list);			
			
			url="/page?page=projSearch";
		}*/
		
		
		
		
		RequestDispatcher view = req.getRequestDispatcher(url);
		view.forward(req,resp);
	}
}