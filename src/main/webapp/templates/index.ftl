[#ftl]
<!DOCTYPE html>
<html lang="en">
  <head> 
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    
    <title>Platform Agent</title>
    
    <link rel="stylesheet" type="text/css" media="screen" href="/css/reset.css"/> 
    <link rel="stylesheet" type="text/css" media="screen" href="/css/text.css"/> 
    <link rel="stylesheet" type="text/css" media="screen" href="/css/960_16_col.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dijit/themes/claro/claro.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css"/>
    
    <script src="/js/dojo/dojo.js" djConfig="parseOnLoad: true"></script>
    
    <script type="text/javascript">
      dojo.require("dijit.form.Form");
      dojo.require("dijit.form.Button");
      dojo.require("dijit.form.ValidationTextBox");
      dojo.require("dijit.layout.TabContainer");
      dojo.require("dijit.layout.ContentPane");
      
      var ipRx = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
      
			dojoConfig = {
				isDebug: true,
				parseOnLoad: true,
				baseUrl: '/',
				modulePaths: {
					"log_items": "log_items",
					"connections" : "connections"
				}
			};
      
      dojo.addOnLoad(function() {
        dojo.connect(dojo.byId('submit'), 'onclick', function(event){
            dojo.stopEvent(event);
            dojo.xhrPost({
              form: 'launchForm',
              handleAs: 'json',
              load: function(data) {
                console.log(data);
                var success = data.success;
                var msg = data.message;
                if(success) {
                  alert(msg);
                }
              },
              error: function(error) {
                console.log(error);
              }
            }) // xhrPost()
          }); // connect()
      });
    </script>
  </head>
  <body>
    <div class="container_16 claro">
      <div id="header" class="grid_16 clearfix">
          <h1>Platform Agent</h1>
      </div>
      <div id="content" class="clearfix">
        <div id="navbar" class="grid_4 clearfix">
          <div class="inner">
            <div class="pagents-link"><a href="#pagents">Platform agents</a></div>
            <div class="connections-link"><a href="#connections">Connections</a></div>
          </div>
        </div>
        <div id="tabpanel" class="grid_12 clearfix">
          <div dojoType="dijit.layout.TabContainer" style="width: 698px; height: 400px;">
            <div dojoType="dijit.layout.ContentPane" title="Launch" selected="true">
              <form id="launchForm" action="" method="POST">
                <dl>
                  <dt><label for="ipAddress">IP Address</label></dt>
                  <dd><input id="ipAddress" name="ipAddress" type="text" /></dd>

                  <dt><label for="path">Path</label></dt>
                  <dd><input id="path" name="path" type="text" /></dd>
                </dl>
                <input id="submit" type="submit" value="Launch!">
              </form>
            </div>
            <div dojoType="dijit.layout.ContentPane" title="Monitor">
              Monitoring...
            </div>
            <div dojoType="dijit.layout.ContentPane" title="Manage">
              Managing...
            </div>
          </div>
        </div>
      </div>
      <div id="footer" class="grid_16 clearfix">
    
      </div>
    </div>
  </body>
</html>
