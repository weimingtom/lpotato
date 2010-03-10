


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link href="frameReady.css" rel="stylesheet" type="text/css"/>
<title>I'm the root document</title>
<script language="JavaScript" type="text/javascript" src="../../js/jquery.js"></script>
<script language="JavaScript" type="text/javascript" src="../../js/firebug.js"></script>
<script language="JavaScript" type="text/javascript" src="../../js/jquery.frameReady.js"></script>
<script language="JavaScript" type="text/javascript">
$(document).ready(function(){ 
	
	$daemach.debug = true;
	
	$('#loadContent').click(function(){
	
		$.frameReady(function(){
				$.blockUI();
				$('<div></div>').addClass('box').appendTo("body").load("ajaxcontent.cfm",$.unblockUI);
			},"top.iFrame", 
			{ load: {type:"script",src:"/js/jquery.blockUI.js",test: "$.blockUI.impl.install"} }, 
			function(){$(':disabled').removeAttr("disabled");	
		});
	});
	
	$('#blockit').click(function(){ 
		$.frameReady(function(){$.blockUI(); },"top.iFrame"); 
	});
	$('#unblockit').click(function(){ 
		$.frameReady(function(){$.unblockUI(); },"top.iFrame"); 
	});
	
	$('#code').hide();
	
	$('#codeHeader').toggle(function(){
		$(this).html("<strong>Hide code:</strong>");
		$('#code').show();
	},function(){
			$(this).html("<strong>Show code:</strong>");
		$('#code').hide();
	});
 });
</script>

</head>
<body class="lighter">
<h3>Loading a div via ajax in an iFrame using $.frameReady();</h3>
<hr />
<div id="codeBlock" class="box2">
<span id="codeHeader" style="cursor:pointer"><strong>Show code:</strong></span>
<div id="code"><pre>

&lt;script language=&quot;JavaScript&quot; type=&quot;text/javascript&quot;&gt;
$(document).ready(function(){ 
	
	$daemach.debug = true;
	
	$('#loadContent').click(function(){
	
		$.frameReady(function(){
				$.blockUI();
				$('&lt;div&gt;&lt;/div&gt;').addClass('box2').appendTo(&quot;body&quot;).load(&quot;ajaxcontent.cfm&quot;,$.unblockUI);
			},&quot;top.iFrame&quot;, 
			{ load: {type:&quot;script&quot;,src:&quot;/js/jquery.blockUI.js&quot;,test: &quot;$.blockUI.impl.install&quot;} }, 
			function(){$(':disabled').removeAttr(&quot;disabled&quot;);	
		});
	});
	
	$('#blockit').click(function(){ 
		$.frameReady(function(){$.blockUI(); },&quot;top.iFrame&quot;); 
	});
	$('#unblockit').click(function(){ 
		$.frameReady(function(){$.unblockUI(); },&quot;top.iFrame&quot;); 
	});
 });
&lt;/script&gt;

</pre></div></div>
<hr />

<input id="loadContent" name="loadContent" type="button" value="Load some content!" /> 
<input id="blockit" name="blockit" type="button" value="Block UI" disabled="disabled" /> 
<input id="unblockit" name="unblockit" type="button" value="Unblock UI" disabled="disabled" />

<hr />
	
	<div id="frameWrapper" style="width:600px;"><br />
		<iframe id="iFrame" name="iFrame" width="600" height="500" src="ajaxiframe.cfm" />
	</div>

</body>
</html>
