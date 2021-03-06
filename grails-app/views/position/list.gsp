
<%@ page import="com.certotrack.Position" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'position.label', default: 'Position')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'position.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="comment" title="${message(code: 'position.comment.label', default: 'Comment')}" />
                        
                            <g:sortableColumn property="latitude" title="${message(code: 'position.latitude.label', default: 'Latitude')}" />
                        
                            <g:sortableColumn property="longitude" title="${message(code: 'position.longitude.label', default: 'Longitude')}" />
                        
                            <g:sortableColumn property="positionDate" title="${message(code: 'position.positionDate.label', default: 'Position Date')}" />
                        
                            <th><g:message code="position.track.label" default="Track" /></th>
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${positionInstanceList}" status="i" var="positionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${positionInstance.id}">${fieldValue(bean: positionInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: positionInstance, field: "comment")}</td>
                        
                            <td>${fieldValue(bean: positionInstance, field: "latitude")}</td>
                        
                            <td>${fieldValue(bean: positionInstance, field: "longitude")}</td>
                        
                            <td><g:formatDate date="${positionInstance.positionDate}" /></td>
                        
                            <td>${fieldValue(bean: positionInstance, field: "track")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${positionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
