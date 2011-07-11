[#ftl]
<!DOCTYPE html>
<html lang="en">
  <head> 
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    
    <title>Platform Agent</title>
    
    <link rel="stylesheet" type="text/css" media="screen" href="/css/reset.css"/> 
    <link rel="stylesheet" type="text/css" media="screen" href="/css/text.css"/> 
    <link rel="stylesheet" type="text/css" media="screen" href="/css/960_16_col.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/jquery-ui-1.8.14.custom.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css"/>
    
    <script type="text/javascript" src="/js/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.8.14.custom.min.js"></script>
    <script type="text/javascript" src="/js/jquery.form.js"></script>
    
    <script type="text/javascript">
      $(function() {
    		$('#tabs').tabs();
    		
    		var options = { 
            target: '#tabs',
            success: handleLaunch,
            dataType: 'json',
            clearForm: true,
            resetForm: true
        };
    		
    		$('#launch-form').ajaxForm(options);
    		
    		$('#submit').button({
          icons: { primary: 'ui-icon-gear' },
          label: 'Launch!'
        });
        
        var agents = [];
    		
    		function handleLaunch(responseText, statusText, xhr, $form)  {
    		  if (responseText["success"] && responseText["success"] == "true")
    		    console.log( "responseText.sndId: " + responseText["sndId"] + "; .host: " + responseText["host"] + "; .path: " + responseText["path"] );
    		  else
    		    console.log( "responseText.success: " + responseText["success"] + "; .message: " + responseText["message"] )
    		    
    		  console.log( "statusText: " + statusText );
    		  console.log( "xhr: " + xhr );
    		  
    		  agents.push(responseText["sndId"]);
          $('#tabs').tabs('add','/rest/agents/' + responseText["sndId"], responseText["host"] + responseText["path"]);
        }
    	});
    </script>
  </head>
  <body>
    <div class="container_16">
      <div id="header" class="grid_16 clearfix">
          <h1>Platform Agent</h1>
      </div>
      <div class="grid_16 clearfix">
        <div id="tabs">
        	<ul>
        		<li><a href="#tabs-1">Launch</a></li>
        	</ul>  
      		<div id="tabs-1">
      		  <form id="launch-form" action="/" method="post">
      		    <fieldset>
      		      <legend>Launch an Agent</legend>
                <p>
                  <label for="ip-address">IP Address</label><br/>
                  <input id="ip-address" type="text" name="ipAddress"/>
                </p>
                <p>
                  <label for="path">Path</label><br/>
                  <input id="path" type="text" name="path"/>
                </p>
                <p>
                  <input id="submit" type="submit" value="Launch"/>
                </p>
              </fieldset>
            </form>
      		</div>
        </div>
      </div>
      <div id="footer" class="grid_16 clearfix">
        
      </div>
    </div>
  </body>
</html>