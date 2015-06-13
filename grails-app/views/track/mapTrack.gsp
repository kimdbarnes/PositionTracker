
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
        data.addRows(${positionInstanceList?.size()});
        <g:each in="${positionInstanceList}" var="p" status="i">
           data.setCell(${i}, 0, ${p.latitude});
           data.setCell(${i}, 1, ${p.longitude});
           data.setCell(${i}, 2, '${p.popup}')
        </g:each>
        var map = new google.visualization.Map(document.getElementById('map_div'));
        map.draw(data, {showTip: true, showLine:true, lineWidth:2, mapType:'hybrid', zoom:8, enableScrollWheel:true});
      }

      google.load('visualization', '1', {packages:['table']});
      google.setOnLoadCallback(drawTable);
      function drawTable() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Date');
        data.addColumn('string', 'Comment');
        data.addColumn('string', 'Lat');
        data.addColumn('string', 'Long');
        data.addRows(${positionInstanceList?.size()});
        <g:each in="${positionInstanceList}" var="p" status="i">
           data.setCell(${i}, 0, '${p.date}');
           data.setCell(${i}, 1, '${p.commentDesc}');
           data.setCell(${i}, 2, '${p.latitudeDesc}');
           data.setCell(${i}, 3, '${p.longitudeDesc}');
        </g:each>
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
            <div id="map_div" style="width: 800px; height: 600px"></div>
            <h1>${trackInstance.trackee.name}'s Positions</h1>
            <div id='table_div'></div>
            <br>
            <br>
        </div>
  </body>

</html>
