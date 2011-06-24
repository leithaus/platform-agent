[#ftl]
<!DOCTYPE html>
<html lang="en">
  <head> 
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    
    <title>Platform Agent</title>
    
    <link rel="stylesheet" type="text/css" media="screen" href="/css/reset.css"/> 
    <link rel="stylesheet" type="text/css" media="screen" href="/css/text.css"/> 
    <link rel="stylesheet" type="text/css" media="screen" href="/css/960_16_col.css"/>
    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dijit/themes/claro/claro.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="/css/main.css"/>
    
    <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojo/dojo.xd.js" djConfig="parseOnLoad: true"></script>
    
    <script type="text/javascript">
        dojo.require("dijit.layout.TabContainer");
        dojo.require("dijit.layout.ContentPane");
        dojo.addOnLoad(function() {
          var tc = new dijit.layout.TabContainer({
              style: "height: 100%; width: 100%;"
          },
          "tc1-prog");

          var cp1 = new dijit.layout.ContentPane({
              title: "Launch",
              content: "Launching..."
          });
          tc.addChild(cp1);

          var cp2 = new dijit.layout.ContentPane({
              title: "Monitor",
              content: "Monitoring..."
          });
          tc.addChild(cp2);

          var cp3 = new dijit.layout.ContentPane({
              title: "Manage",
              content: "Managing..."
          });
          tc.addChild(cp3);

          tc.startup();
        });
    </script>
  </head>
  <body>
    <div class="container_16 claro">
      <div id="header" class="grid_16 clearfix">
          <h1>Platform Agent</h1>
      </div>
      <div id="content" class="grid_16 clearfix" style="width: 950px; height: 400px">
        <div id="tc1-prog"></div>
      </div>
      <div id="footer" class="grid_16 clearfix">
    
      </div>
    </div>
  </body>
</html>
