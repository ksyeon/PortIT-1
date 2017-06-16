<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link href="assets/css/message.css" rel="stylesheet">

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
			<div class="sidebar-toggle-box">
			</div>
			
		</header>
		
		
		<!--header end-->

		<!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
		<!--sidebar start-->
		
		
		<!--sidebar end-->

		<!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
		<!--main content start-->
		<section id="main-content">
			<section class="wrapper site-min-height">
				<div class="col-md-12 mt msg msgDetail">
					<div class="panel-group msgDetailContent">
						<div class="panel panel-default msgBox">
							<div class="panel-heading">
								<span class="pull-left">
									<a href="#">
										<img src="assets/img/you.png" class="img-circle">&nbsp;&nbsp;
										<span class="msgSender">발신자 이름</span>
									</a>
								</span>
								<span class="pull-right"> 
									<a href="msgSend.html">
										<button type="button" class="btn">메세지 보내기</button>
									</a>&nbsp;&nbsp;&nbsp;
									<a href="msgList.html">
										<button type="button" class="btn">목록</button>
									</a>
								</span>								
							</div>
							<div class="panel-body">
								<div class="msgContentBox mb clearfix"> <!-- 반복 -->
									<span class="pull-left col-md-10 msgContent">
										메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.
										메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.
										메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.
										메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.
									</span>
									<span class="pull-right col-md-2">
										<span style="position:absolute; top: 0; right: 0;"><i class="fa fa-clock-o fa-fw"></i>17.05.12 16:34</span>
										<span style="position:absolute; top: 20px; right: 0;"><button type="button" class="btn">삭제</button></span>											
									</span>										
								</div>	
								<div class="msgContentBox mb clearfix"> <!-- 반복 -->
									<span class="pull-left col-md-10 msgContent">
										메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.
										메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.
										메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.
										메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.메세지 내용입니다.
									</span>
									<span class="pull-right col-md-2">
										<span style="position:absolute; top: 0; right: 0;"><i class="fa fa-clock-o fa-fw"></i>17.05.12 16:34</span>											<span style="position:absolute; top: 20px; right: 0;"><button type="button" class="btn">삭제</button></span>											
									</span>										
								</div>
								
								<!-- 페이지네이션 -->
								<div class="center"> 
									1 2 3 4 5
								</div>					
							</div>			
								
						</div> <!-- END - msgBoxOpen -->
					</div> <!-- END - msgBox -->			
						
							
				<!-- 
					<div class="chat-panel panel panel-default chat-boder chat-panel-head">
						<div class="panel-heading">
							<i class="fa fa-comments fa-fw"></i> Chat Box2
								
								
								<div class="btn-group pull-right">
								<div class="input-group">
									<a href="msgList.html">
									<button type="button" class="btn btn-primary btn-sm">돌아가기</button>
									</a> 
									
									<i class="fa fa-chevron-down"></i>
									<ul class="dropdown-menu slidedown">
										<li><a href="#"> <i class="fa fa-refresh fa-fw"></i>Refresh
										</a></li>
										<li><a href="#"> <i class="fa fa-check-circle fa-fw"></i>Available
										</a></li>
										<li><a href="#"> <i class="fa fa-times fa-fw"></i>Busy
										</a></li>
										<li><a href="#"> <i class="fa fa-clock-o fa-fw"></i>Away
										</a></li>
										<li class="divider"></li>
										<li><a href="#"> <i class="fa fa-sign-out fa-fw"></i>Sign
												Out
										</a></li>
									</ul>
								</div>
								</div>
							</div>

							<div class="panel-body">
								<ul class="chat-box">
									<li class="left clearfix"><span class="chat-img pull-left">
											<img src="/Project2/assets/img/you2.png" width="60px" height="60px" alt="User"
											class="img-circle" />
									</span>
										<div class="chat-body">
											<strong>코난범인</strong> <small class="pull-right text-muted">
												<i class="fa fa-clock-o fa-fw"></i>12 mins ago
											</small>
											<p>ㅋㅋㅋㅋㅋㅋㅋ</p>
										</div></li>
									<li class="right clearfix"><span
										class="chat-img pull-right"> 
										<img src="/Project2/assets/img/co2.png" alt="User" width="60px" height="60px" class="img-circle" />
									</span>
										<div class="chat-body clearfix">
											<small class=" text-muted"> <i
												class="fa fa-clock-o fa-fw"></i>13 mins ago
											</small> <strong class="pull-right">코난</strong>
											<p>수수께끼는 풀렸어 범인은 당신이야!</p>
										</div> 
									</li>
								</ul>
							</div>


							<div class="panel-footer">
							
								<div class="input-group">
									<input id="btn-input" type="text" class="form-control input-sm"
										placeholder="Type your message to send..." /> <span
										class="input-group-btn">
										<button class="btn btn-warning btn-sm" id="btn-chat">
											Send</button>
									</span>
								</div>
							</div>

						</div>
						-->
					</div>
				</div>



				<!--  chat box end -->



			</section>
			<! --/wrapper -->
		</section>





		<!-- /MAIN CONTENT -->

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


	<!--common script for all pages-->
	<script src="assets/js/common-scripts.js"></script>

	<!--script for this page-->

	<script>
      //custom select box

      $(function(){
          $('select.styled').customSelect();
      });

  </script>

</body>
</html>
