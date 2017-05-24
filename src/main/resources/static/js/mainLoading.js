/**
 * 
 */
$(document).ready(function(){
		$.ajax({
	            type : "GET",
	            url : "/matches",
	            data : {
	            	startNum:0,
	            	offset:8
	            },
	            error : function(){
	                alert('통신실패!!');
	            },
	            success : function(data){
	                /* alert("통신데이터 값 : " + data.comment) ; */
	                $('.fm-ct-posts').append(data);
	                console.log(data+"test");
	            }
	             
	     });
		
		// Page loading
		$(window).scroll(function() {
			if($(window).scrollTop() + $(window).height() + 1 >= $(document).height()) {
		    	// ajax call get data from server and append to the div
		    	 $('.fm-ct-posts').append($('<div />').addClass('each-post matching')
		    			 .append($('<div />').addClass('posts-up')
			    					.append($('<div />').addClass('posts-title').text('오므라이스 크림 스파게티'))
			    					.append($('<div />').addClass('posts-writer').text('구슬씨'))
			    					.append($('<div />').addClass('posts-time').text('1시간 전'))
			    					.append($('<div />').addClass('posts-like').text('927')
			    							.append($('<img />').addClass('posts-heart').attr('src', '/images/heart_on.svg'))
			    					)
			    			)
			    			.append($('<div />').addClass('posts-down')
			    					.append($('<img />').addClass('half-img').attr('src', '/images/om.png'))
			    					.append($('<img />').addClass('half-img').attr('src', '/images/spa.png'))
			    			)
		    	 )
		    }
		});
		
		// Get page url 
		var stringPathName = window.location.pathname;
		
		$('#random').css("background-image", "url(/images/mario_img.png)");

		$('#ramen').css("background-image", "url(/images/ramen.png)");
		$('#cutlet').css("background-image", "url(/images/cutlet.png)");
		$('#sushi').css("background-image", "url(/images/sushi.png)");

		$('#ramen2').css("background-image", "url(/images/ramen.png)");
		$('#cutlet2').css("background-image", "url(/images/cutlet.png)");
		$('#sushi2').css("background-image", "url(/images/sushi.png)");


		$('#omu').css("background-image", "url(/images/om.png)");
		$('#spa').css("background-image", "url(/images/spa.png)");

		$('#omu2').css("background-image", "url(/images/om.png)");
		$('#spa2').css("background-image", "url(/images/spa.png)");
})
