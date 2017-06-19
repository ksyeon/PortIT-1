<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="assets/css/search.css" rel="stylesheet">
		<section class="container">
			<section class="wrapper site-min-height">
				<div  class="col-md-12 mt search" id="searchMem">
					<!-- 검색어 검색 폼 -->
					<div class="col-md-12 mt mb">
						<form class="col-md-10 searchKeyword" method="post" action="">
							<div class="form-group col-md-11">
								<input type="text" class="form-control" />
							</div>
							<button type="submit" class="col-md-1 btn common"><i class="fa fa-search"></i></button>
						</form>
						<div class="col-md-2 collapsed sortingBtn text-right" data-toggle="collapse" data-parent="#searchPf" href="#memSorting">
							<button type="button" class="btn common updown">
								조건 검색&nbsp;&nbsp;<i class="fa fa-chevron-down"></i>
							</button>
						</div>	
					</div>
					<!-- 조건 검색 box -->					
					<div class="searchSorting col-md-12 collapse" id="memSorting">	
						<form class="" method="post" action="">									
							<div class="">
								<div class="sortKey col-md-1"><b>정렬</b></div>
								<div class="col-md-11">
									<a href="#">최신등록순</a>
									<a href="#">인기순</a> 
									<a href="#">랜덤</a>					
								</div>
							</div>							
							<br><br>										
							<div class="sortKey col-md-1"><b>태그</b></div>										
							<div class="col-md-11">	
								<!-- 인기 태그 6개 띄우기 -->							
								<input class="btn poptag" type="button"  value="java" onclick="fnAppendItem()"/>
								<input class="btn poptag" type="button"  value="c" onclick="fnAppendItem()"/>
								<input class="btn poptag" type="button"  value="c++" onclick="fnAppendItem()"/>
								<input class="btn poptag" type="button"  value="c#" onclick="fnAppendItem()"/>
								<input class="btn poptag" type="button"  value="jsp" onclick="fnAppendItem()"/>
								<input class="btn poptag" type="button"  value="servlet" onclick="fnAppendItem()"/>								
								.....
							</div>
							<br><br>
							<div class="col-md-offset-1 col-md-4">
								<input type="text" class="form-control taginput" id="language" placeholder="검색하고 싶은 태그를 입력하세요." onchange="fnAppendItem2()" />
							</div>		
							<br><br>
							<hr />
							<div id="itemList" class="col-md-9"></div>		
							<button type="submit" class="btn common col-md-2">조건 검색하기</button>									
						</form>
					</div> <!-- END - 조건 검색 box -->	
					
					
					<!-- 멤버 결과 -->
					<div class="memResult mt mb" id="memResult">
						<!-- 첫번째 member-->
						<div class="col-md-3 mb">
	          				<div class="member-simple">
		          				<div class="simple-content text-center">	      
			          				<img class="memImg img-circle" alt="avatar" src="assets/img/friends/fr-06.jpg"/>   
			         				<div>
			         					<div class="memName"><a href="">멤버 이름</a></div>
			         					<div class="memTag"><a href="">#태그&nbsp;</a></div>
			         					<div class="memFollow">
			         						<span class="fa fa-user"></span>&nbsp;&nbsp;
			         						<span class="memFollowCount">135</span>
			         					</div>
			         				</div>
		          				</div>          				
	          				</div>
	          			</div>
						<!-- 두번째 member-->
						<div class="col-md-3 mb">
	          				<div class="member-simple">
		          				<div class="simple-content text-center">	      
			          				<img class="memImg img-circle" alt="avatar" src="assets/img/friends/fr-06.jpg"/>   
			         				<div>
			         					<div class="memName"><a href="">멤버 이름</a></div>
			         					<div class="memTag"><a href="">#태그&nbsp;</a></div>
			         					<div class="memFollow">
			         						<span class="fa fa-user"></span>&nbsp;&nbsp;
			         						<span class="memFollowCount">135</span>
			         					</div>
			         				</div>
		          				</div>          				
	          				</div>
	          			</div>
						<!-- 세번째 member-->
						<div class="col-md-3 mb">
	          				<div class="member-simple">
		          				<div class="simple-content text-center">	      
			          				<img class="memImg img-circle" alt="avatar" src="assets/img/friends/fr-06.jpg"/>   
			         				<div>
			         					<div class="memName"><a href="">멤버 이름</a></div>
			         					<div class="memTag"><a href="">#태그&nbsp;</a></div>
			         					<div class="memFollow">
			         						<span class="fa fa-user"></span>&nbsp;&nbsp;
			         						<span class="memFollowCount">135</span>
			         					</div>
			         				</div>
		          				</div>          				
	          				</div>
	          			</div>
						<!-- 네번째 member-->
						<div class="col-md-3 mb">
	          				<div class="member-simple">
		          				<div class="simple-content text-center">	      
			          				<img class="memImg img-circle" alt="avatar" src="assets/img/friends/fr-06.jpg"/>   
			         				<div>
			         					<div class="memName"><a href="">멤버 이름</a></div>
			         					<div class="memTag"><a href="">#태그&nbsp;</a></div>
			         					<div class="memFollow">
			         						<span class="fa fa-user"></span>&nbsp;&nbsp;
			         						<span class="memFollowCount">135</span>
			         					</div>
			         				</div>
		          				</div>          				
	          				</div>
	          			</div>
						<!-- 다섯번째 member-->
						<div class="col-md-3 mb">
	          				<div class="member-simple">
		          				<div class="simple-content text-center">	      
			          				<img class="memImg img-circle" alt="avatar" src="assets/img/friends/fr-06.jpg"/>   
			         				<div>
			         					<div class="memName"><a href="">멤버 이름</a></div>
			         					<div class="memTag"><a href="">#태그&nbsp;</a></div>
			         					<div class="memFollow">
			         						<span class="fa fa-user"></span>&nbsp;&nbsp;
			         						<span class="memFollowCount">135</span>
			         					</div>
			         				</div>
		          				</div>          				
	          				</div>
	          			</div>
						<!-- 여섯번째 member-->
						<div class="col-md-3 mb">
	          				<div class="member-simple">
		          				<div class="simple-content text-center">	      
			          				<img class="memImg img-circle" alt="avatar" src="assets/img/friends/fr-06.jpg"/>   
			         				<div>
			         					<div class="memName"><a href="">멤버 이름</a></div>
			         					<div class="memTag"><a href="">#태그&nbsp;</a></div>
			         					<div class="memFollow">
			         						<span class="fa fa-user"></span>&nbsp;&nbsp;
			         						<span class="memFollowCount">135</span>
			         					</div>
			         				</div>
		          				</div>          				
	          				</div>
	          			</div>
					</div>	
				</div>
				
				<!-- 페이지네이션 -->
				<div class="col-md-12 text-center mt mb">
					&lt; 1 2 3 4 5 &gt;
				</div>
			</section>
			<!--/wrapper -->
		</section>

	
	<!-- detail search bar -->
	<script src="assets/js/search.js"></script>

	<!--script for this page-->
	<script>
		$(document).ready(function(){
			$('.updown').on('click',function(){
				var icon = $(this).find('i');
				
		        if (icon.hasClass("fa-chevron-down")){
		        	icon.addClass("fa-chevron-up").removeClass("fa-chevron-down");
		    	} 
		        else {
		        	 icon.addClass("fa-chevron-down").removeClass("fa-chevron-up");
		        }
		    });
		});
	</script>
</body>
</html>