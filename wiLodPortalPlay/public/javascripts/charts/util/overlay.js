function showOverlaidImage(srcContent) {
	$('#overlay-image').attr('src', srcContent);
	
    var height = Math.max(document.body.scrollHeight, document.body.offsetHeight, 
               document.documentElement.clientHeight, document.documentElement.scrollHeight, 
               document.documentElement.offsetHeight );
	
    $("#overlay").height(height).show();
    
    $('#overlay-image').load(function() {
	    var overlayFrame = $("#overlay-center");
	    
	    var wSize = windowSize();
	    
	    var frameLeft = wSize.width / 2 - overlayFrame.width() / 2;
	    var frameTop = wSize.height / 2 - overlayFrame.height() / 2;

	    overlayFrame.css( { left : frameLeft + 'px', top : frameTop + 'px' } );
    });
    
    Scrolling.disableScroll();	
    
    $("#overlay-close").click(function() {
		$("#overlay").hide();
		Scrolling.enableScroll();
	});
}

function windowSize() {
  var myWidth = 0, myHeight = 0;
  if( typeof( window.innerWidth ) == 'number' ) {
    //Non-IE
    myWidth = window.innerWidth;
    myHeight = window.innerHeight;
  } else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
    //IE 6+ in 'standards compliant mode'
    myWidth = document.documentElement.clientWidth;
    myHeight = document.documentElement.clientHeight;
  } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
    //IE 4 compatible
    myWidth = document.body.clientWidth;
    myHeight = document.body.clientHeight;
  }
  
  return { width: myWidth, height: myHeight };
}