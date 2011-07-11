[#ftl]
<div id="innerTabs">
  <ul>
		<li><a href="#innerTabs-1">Monitor</a></li>
		<li><a href="#innerTabs-2">Manage</a></li>
		<li><a href="#innerTabs-3">Connect</a></li>
  </ul>
  <div id="innerTabs-1">Loading...</div>
  <div id="innerTabs-2">Loading...</div>
  <div id="innerTabs-3">Loading...</div>
</div>
<script type="text/javascript">
  var path = '/rest/agents/8283c372-1363-4b9a-8092-c7d54404cf69';
  
    $('#innerTabs').tabs();
    $('#innerTabs').tabs('url', 0, path + '/logs');
    $('#innerTabs').tabs('load', 0);
    $('#innerTabs').tabs('url', 1, path + '/agents');
    $('#innerTabs').tabs('url', 2, path + '/cnxns');
</script>