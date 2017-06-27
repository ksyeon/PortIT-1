<%@page import="portit.model.dto.Timeline"%>
<%@page import="portit.model.dto.Project"%>
<%@page import="portit.model.dto.Developer"%>
<%@page import="portit.model.dto.Portfolio"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<section class="container">
<section class="wrapper site-min-height">
	<div class="col-md-12 col-sm-12 col-xs-12 mt" id="main">
		<div class="recommend">
			<b>추천</b>&nbsp;&nbsp;&nbsp; <a href="#pfRecommend" data-toggle="tab">포트폴리오</a>
			&nbsp;&nbsp; <a href="#memRecommend" data-toggle="tab">개발자</a>
			&nbsp;&nbsp; <a href="#projRecommend" data-toggle="tab">모집</a>
			&nbsp;&nbsp;
		</div>
		<div class="tabs-below tabbable col-md-12">
			<div class="tab-content">
				<!-- 포트폴리오 패널 -->
				<div class="tab-pane active" id="pfRecommend">
					<div class="row mt">
						<!-- 포트폴리오 패널 -->
						<div class="tab-pane active" id="pfRecommend">
							<div class="row mt">
					
							<%
								List list = portfolio.portfolio_info();
								//request.setAttribute("list", list);
								for (int i = 0; i < 4; i++) {
									Portfolio port = (Portfolio) list.get(i);
							%>			
								<!-- 첫번째 포트폴리오 -->
								<div class="col-md-3 mb">
									<div class="portfolio-simple">
										<div class="pfImg"></div>	
										<div class="pfInfo">
											<div class="simple-content">
												<div class="pfTag">
													<a href=""># <%=port.getTag_name()%>&nbsp;</a></div>
						
											<div class="pfTitle">
													<a href=""><%=port.getPf_title()%></a>
												</div>
												<div class="pfBottom">
													<span class="pfmemName"><a href=""><%=port.getProf_name()%></a></span> 
													<span class="pfLikeCount"><span class="fa fa-heart"></span>&nbsp;&nbsp;<%=port.getPf_like()%></span>
												</div>
											</div>
										</div>
									</div>
								</div>	
							<%
								}
							%>							
							</div>
						</div>

		
					<!-- 개발자 패널 -->
						<div class="tab-pane" id="memRecommend">
							<div class="row mt">
							<%
								List list2 = developer.developer_info();
								for (int i = 0; i < 4; i++) {
									Developer dev = (Developer) list2.get(i);
							%>				
								<!-- 첫 번째 member-->
								<div class="col-md-3 mb">
									<div class="member-simple">
										<div class="simple-content text-center">
											<img class="memImg img-circle" alt="avatar"
												src="<%=dev.getProf_img()%>" />
											<div>
												<div class="memName">
													<a href=""><%=dev.getProf_name()%></a>
												</div>
												<div class="memTag">
													<a href="">#<%=dev.getTag_name()%>&nbsp;</a>
												</div>
	
												<div class="memFollow">
													<span class="fa fa-user"></span>&nbsp;&nbsp; <span
														class="memFollowCount"><%=dev.getProf_follower()%></span>
												</div>
											</div>
										</div>
									</div>
								</div>
							<%
								}
							%>		
							</div>
						</div>
		
						<!-- 모집 패널 -->
						<div class="tab-pane" id="projRecommend">
							<div class="row mt">
								<div class="col-lg-12">
									<div class="row">
								<%
									List list3 = project.project_info();
									for (int i = 0; i < list3.size(); i++) {
										Project proj = (Project) list3.get(i);
									
								%>			
										<!-- 첫번째 모집 -->
										<div class="col-md-3 mb">
											<div class="project-simple">
												<div class="simple-content text-center">
													<div class="pjTag">
														<a href=""> #<%=proj.getTag_name() %>&nbsp;</a>
													</div>
													<div class="pjTitle">
														<a href=""> </a>
													</div>
													<div class="pjInfo">
														<span class="pjField"><a href="">모집 분야(태그)</a></span>&nbsp;/&nbsp;
														<span class="pjTo"><%=proj.getProj_to() %></span>
													</div>
													<div class="pjRegiEndDate">
														<span>마감일까지</span>&nbsp;&nbsp; 
														<span class="pjDday">D&nbsp;-&nbsp;<span>// 시간 함수로 (마감일 - 현재일)</span></span>
													</div>
												</div>
											</div>
										</div>
									<%} %>	
									</div>									
								</div>
							</div>
						</div>
					</div>
				</div>

		<hr class="mainLine col-md-12" />

			<!-- Timeline page start -->
				<div class="timeline col-md-12">
					<h3 align="center">
						<b> Timeline </b>
					</h3>					
					<%
						List list4 = timeline.timeline_info();
						for (int i = 0; i < list4.size(); i++) {
							Portfolio time = (Portfolio) list4.get(i);
						
					%>	
					<!-- 첫번째 타임라인 -->
					<div class="col-md-12 mt">
						<div class="portfolio-timeline">
							<div class="pfTlType">
								<span class="glyphicon glyphicon-heart"></span> <span
									class="pfTypeText">[멤버이름]님이 내 포트폴리오를 좋아합니다.</span>
							</div>
							<span class="pfInfo">
								<div>
									<span class="pfTitle"><a href=""><%=time.getPf_title() %></a></span> <span
										class="pfLike"> <span class="glyphicon glyphicon-heart"></span>
										<span class="pfLikeCount">234</span>
									</span>
								</div>
								<div class="pfmemName">
									<span class="fa fa-user"><%=time.getProf_name() %></span>&nbsp;&nbsp; <span><a
										href=""></a></span>
								</div>
								<div class="pfTag">
									<a href="">#<%=time.getTag_name() %>&nbsp;</a>
								</div>
							</span> <span class="pfImage"> <span><img
									src="<%=time.getMl_path() %>" /></span> <span><img
									src="assets/img/instagram.jpg" /></span> <span><img
									src="assets/img/instagram.jpg" /></span>
							</span>
						</div>
					</div>
				<%} %>
					
				</div>
			</div>
	</section>
</section>
<!-- Timeline page end -->