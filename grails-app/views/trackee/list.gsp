
<%@ page import="com.certotrack.Trackee" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'trackee.label', default: 'Trackee')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'trackee.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="emailAddress" title="${message(code: 'trackee.emailAddress.label', default: 'Email Address')}" />
                        
                            <g:sortableColumn property="name" title="${message(code: 'trackee.name.label', default: 'Name')}" />
                        
                            <g:sortableColumn property="specialCode" title="${message(code: 'trackee.specialCode.label', default: 'Special Code')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${trackeeInstanceList}" status="i" var="trackeeInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${trackeeInstance.id}">${fieldValue(bean: trackeeInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: trackeeInstance, field: "emailAddress")}</td>
                        
                            <td>${fieldValue(bean: trackeeInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: trackeeInstance, field: "specialCode")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${trackeeInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
