

<%@ page import="com.certotrack.Track" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'track.label', default: 'Track')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${trackInstance}">
            <div class="errors">
                <g:renderErrors bean="${trackInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${trackInstance?.id}" />
                <g:hiddenField name="version" value="${trackInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createdByEmail"><g:message code="track.createdByEmail.label" default="Created By Email" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackInstance, field: 'createdByEmail', 'errors')}">
                                    <g:textField name="createdByEmail" value="${trackInstance?.createdByEmail}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="createdDate"><g:message code="track.createdDate.label" default="Created Date" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackInstance, field: 'createdDate', 'errors')}">
                                    <g:datePicker name="createdDate" precision="day" value="${trackInstance?.createdDate}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="track.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${trackInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="positions"><g:message code="track.positions.label" default="Positions" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackInstance, field: 'positions', 'errors')}">
                                    
<ul>
<g:each in="${trackInstance?.positions?}" var="p">
    <li><g:link controller="position" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="position" action="create" params="['track.id': trackInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'position.label', default: 'Position')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="trackee"><g:message code="track.trackee.label" default="Trackee" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackInstance, field: 'trackee', 'errors')}">
                                    <g:select name="trackee.id" from="${com.certotrack.Trackee.list()}" optionKey="id" value="${trackInstance?.trackee?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
