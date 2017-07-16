package portit.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import portit.model.dao.PortfolioDao;
import portit.model.dao.ProfileDao;
import portit.model.dto.Media;
import portit.model.dto.Portfolio;
import portit.model.dto.Tag;

/**
 * 포트폴리오 작성 컨트롤러
 *
 */
public class PortfolioAddController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PortfolioDao portfolioDao = new PortfolioDao();
		ProfileDao profileDao = new ProfileDao();
		
		// UploadServlet이 전달해준 데이터 받아오기
		Map<String, Object> formData = (Map<String, Object>) req.getAttribute("formData");

		// 태그, 공동 작업자 관련 처리
		List<Tag> languageTagList = (List<Tag>) formData.get("tagLang");
		for (Tag tag : languageTagList) {
			tag.setTag_use_type("portfolio");
		}
		List<Tag> toolTagList = (List<Tag>) formData.get("tagTool");
		for (Tag tag : toolTagList) {
			tag.setTag_use_type("portfolio");
		}
		List<Tag> fieldTagList = (List<Tag>) formData.get("tagField");
		for (Tag tag : fieldTagList) {
			tag.setTag_use_type("portfolio");
		}
		/*List<Profile> coworkerList = new ArrayList<Profile>();
		if (formData.get("final_result_id") != null || formData.get("final_result_id").toString().length() > 0) {
			String[] findUser = formData.get("final_result_id").toString().split(",");
			int[] findUser2 = new int[findUser.length];
			for (int i = 0; i < findUser.length; i++) {
				findUser2[i] = Integer.parseInt(findUser[i]);
			}
			for (int i = 0; i < findUser.length; i++) {
				coworkerList.add(profileDao.getProfile(findUser2[i]));
			}
		}*/
		
		for (String key : formData.keySet()) {
			if (formData.get(key) != null) {
				System.out.println(key + " : " + formData.get(key));
			}
		}

		// 파일 관련 처리
		List<Media> mediaList = (List<Media>) formData.get("fileList");
		if (mediaList != null && mediaList.size() > 0) {
			for (int i = 0; i < mediaList.size(); i++) {
				mediaList.get(i).setMl_type("portfolio");
			}
		}		

		// DTO에 추가
		String viewUrl = "";
		
		Portfolio portfolio = new Portfolio();
		try {
			portfolio.setMem_id(Integer.parseInt(formData.get("mem_id").toString()))
					.setPf_title((String) formData.get("pf_title"))
					.setPf_intro(formData.get("pf_intro") != null
							? new String(((String) formData.get("pf_intro")).getBytes("UTF-8"), "UTF-8").replaceAll("\\r\\n", "<br />") : "")
					.setPf_startdate(
							(Date) new SimpleDateFormat("yyyy-MM-dd").parse((String) formData.get("pf_startdate")))
					.setPf_enddate((Date) new SimpleDateFormat("yyyy-MM-dd").parse((String) formData.get("pf_enddate")))
					.setPf_numofperson(Integer.parseInt((String) formData.get("pf_numofperson")))
					.setPf_url((String) formData.get("pf_url")).setPf_tags_language(languageTagList)
					.setPf_tags_tool(toolTagList).setPf_tags_field(fieldTagList).setPf_mediaList(mediaList)
					/*.setPf_coworkers(coworkerList)*/;
			// DAO의 추가 메서드 호출
			int pf_id = portfolioDao.insert(portfolio);
			
			// 뷰 URL 반환
			viewUrl = "rdr:/view?type=portfolio&id="+String.valueOf(pf_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return viewUrl;
	}

}