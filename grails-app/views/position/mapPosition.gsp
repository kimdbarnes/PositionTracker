
<%@ page import="com.certotrack.Track" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'track.label', default: 'Track')}" />
        <title>Map Track</title>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["map"]});
      google.setOnLoadCallback(drawMap);
      function drawMap() {
        var data = new google.visualization.DataTable();
        data.addColumn('number', 'Lat');
        data.addColumn('number', 'Lon');
        data.addColumn('string', 'Date');
        data.addRows(1);
        data.setCell(0, 0, ${positionInstance.latitude});
        data.setCell(0, 1, ${positionInstance.longitude});
        data.setCell(0, 2, "${positionInstance.popup}")
        var map = new google.visualization.Map(document.getElementById('map_div'));
        map.draw(data, {showTip: true, showLine:true, lineWidth:1, mapType:'hybrid', zoom:8, enableScrollWheel:true});
      }

      google.load('visualization', '1', {packages:['table']});
      google.setOnLoadCallback(drawTable);
      function drawTable() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('string', 'Comment');
        data.addColumn('string', 'Lat');
        data.addColumn('string', 'Long');
        data.addRows(1);
        data.setCell(0, 0, '${positionInstance.date}');
        data.setCell(0, 1, '${positionInstance.commentDesc}');
        data.setCell(0, 2, '${positionInstance.latitudeDesc}');
        data.setCell(0, 3, '${positionInstance.longitudeDesc}');
        var table = new google.visualization.Table(document.getElementById('table_div'));
        table.draw(data, {showRowNumber: true});
      }
    </script>
  </head>


  <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1>Map Track: ${trackInstance.name}</h1>
            <g:if test="${flash.message}">
               <div class="message">${flash.message}</div>
            </g:if>
            <div id="map_div" style="width: 400px; height: 300px"></div>
            <h1>Miss Adventure's Positions</h1>
            <div id='table_div'></div>
        </div>
  </body>

</html>
