<%@page import="portit.model.dto.Project"%>
<%@page import="portit.model.dto.Portfolio"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="Dashboard">
<meta name="keyword"
	content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

<title>DASHGUM - Bootstrap Admin Template</title>

<!-- Bootstrap core CSS -->
<link href="assets/css/bootstrap.css" rel="stylesheet">
<!--external css-->
<link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />

<!-- Custom styles for this template -->
<link href="assets/css/style.css" rel="stylesheet">
<link href="assets/css/style-responsive.css" rel="stylesheet">
<link href="assets/css/custom.css" rel="stylesheet">
<link href="assets/css/search.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<section id="container">
		<!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
		<!--header start-->
		<header class="header black-bg">
			<!--logo start-->
			<a href="index.html" class="logo"><b>Port IT</b></a>
			<!--logo end-->
			<div class="nav notify-row" id="top_menu">
				<!--  menu start -->
				<ul class="nav top-menu">
					<!-- settings start -->
					<li><a href="">Member</a></li>
					<li><a href="">Portfolio</a></li>
					<li><a href="">Project</a>
					<li>
					<li><a href="">Community</a></li>
				</ul>
				<!--  menu end -->
			</div>

			<div class="top-menu-right">
				<ul class="nav pull-right top-menu">
					<li>
						<form class="form-inline top-menu-search" method="post"
							action="/search?cmd=SEARCH">
							<div class="input-group">
								<input type="text" class="form-control round-form" name="search"
									size="20" placeholder="통합 검색" /> <span class="input-group-btn">
									<button type="submit" class="btn btn-default round-form">
										<span class="glyphicon glyphicon-search"></span>
									</button>
								</span>
							</div>
						</form>
					</li>
					<li id="header_inbox_bar" class="dropdown"><a
						data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
							<i class="fa fa-bell"></i> <span class="badge bg-theme"></span>
					</a>
						<ul class="dropdown-menu extended inbox">
							<div class="notify-arrow notify-arrow-yellow"></div>
							<li>
								<p class="yellow">Notification</p>
							</li>
							<li><a href=""> <span>구분</span>&nbsp;/&nbsp; <span
									class="time">Just now</span> <span class="message">[누구누구]님이
										내 포트포리오를 좋아합니다.</span>
							</a></li>
							<li><a href="">더보기</a></li>
						</ul></li>
					<li id="header_inbox_bar" class="dropdown mypage"><a
						data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
							<img alt="avatar" src="assets/img/ui-danro.jpg"
							class="img-circle">
					</a>
						<ul class="dropdown-menu extended inbox">
							<div class="notify-arrow notify-arrow-yellow"></div>
							<li>
								<p class="yellow">김수연 님</p>
							</li>
							<li><a href="">내 프로필</a></li>
							<li><a href="">팔로잉한 멤버</a> <a href="">북마크한 포트폴리오</a></li>
							<li><a href="">개인정보 설정</a></li>
							<li><a class="logout" href="login.html">로그아웃</a></li>
						</ul></li>
				</ul>
			</div>
		</header>
		<!--header end-->
	</section>


	<!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
	<!--main content start-->
	<section class="container">
		<section class="wrapper site-min-height">
			<div class="col-md-12 mt search" id="searchPf">
				<!-- 검색어 검색 폼 -->

				<div class="col-md-12 mt mb">
					<form class="col-md-10 searchKeyword" method="post"
						action="/SearchView?cmd=PROJSEARCH">
						<div class="form-group col-md-11">
							<input type="text" class="form-control" name="projSearch" value="${sessionScope.search}"/>
						</div>
						<button type="submit" class="col-md-1 btn common" id="">
							<i class="fa fa-search"></i>
						</button>
					</form>
					<div class="col-md-2 collapsed sortingBtn text-right"
						data-toggle="collapse" data-parent="#searchPf"
						href="#searchSorting">
						<button type="button" class="btn common updown">
							조건 검색&nbsp;&nbsp;<i class="fa fa-chevron-down"></i>
						</button>
					</div>
				</div>
				<!-- 조건 검색 box -->
				<div class="searchSorting col-md-12 collapse" id="searchSorting">
					<form class="" method="post" name="detailsearch" action="/detailSearch?cmd=PROJDETAIL">
						<input type="hidden" name="list_value"/>
						<div class="">
							<div class="sortKey col-md-1">
								<b>정렬</b>
							</div>
							<div class="col-md-11">
								<a href="javascript:detailSearch(5)">최신순</a> 
								<a href="javascript:detailSearch(6)">D-day</a> 
								<a href="#">랜덤</a>
							</div>
						</div>
						<br> <br>
						<div class="sortKey col-md-1">
							<b>태그</b>
						</div>
						<div class="col-md-11">
							<!-- 인기 태그 6개 띄우기 -->
							<input class="btn poptag" type="button" value="JAVA" name="language" onclick="fnAppendItem()" /> 
							<input class="btn poptag" type="button" value="C" name="language" onclick="fnAppendItem()" /> 
							<input class="btn poptag" type="button" value="c++" name="language" onclick="fnAppendItem()" /> 
							<input class="btn poptag" type="button" value="c#" name="language" onclick="fnAppendItem()" /> 
							<input class="btn poptag" type="button" value="jsp" name="language" onclick="fnAppendItem()" /> 
							<input class="btn poptag" type="button" value="servlet" name="language" onclick="fnAppendItem()" /> .....
						</div>
						<br> <br>
						<div class="col-md-offset-1 col-md-4">
							<input type="text" class="form-control taginput" id="language" name="language2"
								placeholder="검색하고 싶은 태그를 입력하세요." onchange="fnAppendItem2()" />
						</div>
						<br> <br>
						<hr />
						<div id="itemList" class="col-md-9"></div>
						<button type="submit" class="btn common col-md-2">조건 검색하기</button>
					</form>
				</div>
				<!-- END - 조건 검색 box -->
			

		<c:if test="${proj_list.size() != 0 && proj_list.size()>0 }">
				<c:forEach begin="0" end="${proj_list.size()-1}" var="i" >	
					<!-- 프로젝트 -->
						<div class="col-md-12 mb">
	          				<div class="project-list">
		          				<span class="pjInfoText">
		          					<div class="pjTitle"><a href="javascript:proj_title('${proj_list[i].proj_id}')">${proj_list[i].proj_title}</a></div>
		          					<div class="pjmemName"><span class="fa fa-user"></span>&nbsp;&nbsp;<a href=""></a></div>
		          		
		          					<div class="pjIntro">${proj_list[i].proj_intro}</div>
		          					<div class="pjTag"><a href="javascript:tag_name('${mem_list[i].tag_name}')">#${proj_list[i].tag_name}&nbsp;</a></div>         					
	          					</span>
	          					<span class="pjInfoTable">
	          						<table class="table text-center">
	          							<tr><td>백엔드개발자</td></tr>
	          							<tr><td>${proj_list[i].proj_to} 명</td></tr>
	          							<tr><td>마감일까지 D&nbsp;-&nbsp;5</td></tr>
	          							<tr><td></td></tr>
	          						</table>
	          					</span>
	          				</div>          			
						</div>
						<br><br>
				</c:forEach>
			</c:if>	
			<c:if test="${proj_list.size() == 0 }">
				검색된 결과가 없습니다.
			</c:if>			
				</div>

		
		
		</section>
		<! --/wrapper -->
	</section>

	<!--main content end-->
	<!--footer start-->
	<footer class="site-footer">
		<div class="text-center">
			2014 - Alvarez.is <a href="blank.html#" class="go-top"> <i
				class="fa fa-angle-up"></i>
			</a>
		</div>
	</footer>
	<!--footer end-->
	</section>

	<!-- js placed at the end of the document so the pages load faster -->
	<script src="assets/js/jquery.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
	<script class="include" type="text/javascript"
		src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
	<script src="assets/js/jquery.scrollTo.min.js"></script>
	<script src="assets/js/jquery.nicescroll.js" type="text/javascript"></script>
	<script src="assets/js/jquery-3.2.1.min.js"></script>
	
	<!--common script for all pages-->
	<script src="assets/js/common-scripts.js"></script>

	<!-- detail search bar -->
	<script src="assets/js/search.js"></script>

	<!--script for this page-->
	<script>
		$(document).ready(
				function() {
					$('.updown').on(
							'click',
							function() {
								var icon = $(this).find('i');

								if (icon.hasClass("fa-chevron-down")) {
									icon.addClass("fa-chevron-up").removeClass(
											"fa-chevron-down");
								} else {
									icon.addClass("fa-chevron-down")
											.removeClass("fa-chevron-up");
								}
							});
				});
	</script>
	
	
	<script>
		//list_value = 5 이면 최신순 정렬 6이면 인기순
		function detailSearch(list_value){
			document.detailsearch.list_value.value = list_value;
			document.detailsearch.submit();
		}	
	</script>
</body>
</html>