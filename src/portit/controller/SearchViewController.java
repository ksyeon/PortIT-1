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

import portit.model.dao.Portfolio_ViewDao;
import portit.model.dao.SearchDao;


@WebServlet(urlPatterns="/SearchView")
public class SearchViewController extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html; charset=UTF-8");
		String cmd = req.getParameter("cmd");
		String url = null;
		HttpSession session = req.getSession();
		
		session.removeAttribute("search");
		
		//검색어 결과
		String pfSearch = req.getParameter("pfSearch");
		String memSearch = req.getParameter("memSearch");
		String projSearch = req.getParameter("projSearch");	
		
		session.setAttribute("pfSearch", pfSearch);
		session.setAttribute("memSearch", memSearch);
		session.setAttribute("projSearch", projSearch);
		
		//포트폴리오 Dao 호출
		//Controller -> Model로 넘겨주기
		SearchDao searchDao = new SearchDao();
		List list;
		boolean lineup = true;
		
		if(memSearch==null && projSearch==null){	
			list = searchDao.searchAll_port(pfSearch,lineup);		
			req.setAttribute("port_list", list);
		}
		else if(pfSearch==null && projSearch==null){
			list = searchDao.searchAll_member(memSearch,true);
			req.setAttribute("mem_list", list);
		}
		else{
			list = searchDao.searchAll_proj(projSearch,true);
			req.setAttribute("proj_list", list);
		}	
	
		
		if(cmd.equals("PFSEARCH")){
			url="/pfSearch.jsp";
		}
		else if(cmd.equals("MEMSEARCH")){
			url="/memSearch.jsp";
		}
		else if(cmd.equals("PROJSEARCH")){
			url="/projSearch.jsp";
		}


		RequestDispatcher view = req.getRequestDispatcher(url);
		view.forward(req,resp);
		
		
	/*
		//0,1,2(3개까지 선택 가능)
		String[] lang = new String[2];
		
		String st1 = ""; 
		String st2 = ""; 
		String st3 = ""; 
		
		String language = req.getParameterValues("language");
		
		for(int j=0; j<=2; j++){
			for(int i=0; i<language.length; i++){
				if(language != null){
					lang[j] = language[i];
					continue;
				}
			}
			st1 = lang[0];
			st2 = lang[1];
			st3 = lang[2];
		}
		
		List cho_lang = null;
		
		cho_lang.add(st1);
		cho_lang.add(st2);
		cho_lang.add(st3);	
		
		
		//포트폴리오 Dao 호출
		SearchDao searchDao = new SearchDao();	
		List port_list;
		List port_list2;
		
		//port_list = searchDao.searchAll_port(pfSearch);		
		//cho_lang = searchDao.searchAll_port(st1, st2);
		//port_list2 = searchDao.searchAll_port(language);
				
		//Controller -> Model로 넘겨주기 위한 변수
		req.setAttribute("port_list", port_list);
		//req.setAttribute("language", cho_lang);

	*/	
	}
}