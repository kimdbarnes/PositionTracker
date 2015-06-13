
<%@ page import="com.certotrack.Position" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'position.label', default: 'Position')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="position.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: positionInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="position.comment.label" default="Comment" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: positionInstance, field: "comment")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="position.latitude.label" default="Latitude" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: positionInstance, field: "latitude")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="position.longitude.label" default="Longitude" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: positionInstance, field: "longitude")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="position.positionDate.label" default="Position Date" /></td>
                            
                            <td valign="top" class="value"><g:formatDate date="${positionInstance?.positionDate}" /></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="position.track.label" default="Track" /></td>
                            
                            <td valign="top" class="value"><g:link controller="track" action="show" id="${positionInstance?.track?.id}">${positionInstance?.track?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                 
                        <tr class="prop">
                            <td><g:link action="mapPosition" id="${positionInstance.id}">Map It!</g:link></td>
                            <td>&nbsp;</td>
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${positionInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
