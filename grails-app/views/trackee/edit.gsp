

<%@ page import="com.certotrack.Trackee" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'trackee.label', default: 'Trackee')}" />
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
            <g:hasErrors bean="${trackeeInstance}">
            <div class="errors">
                <g:renderErrors bean="${trackeeInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${trackeeInstance?.id}" />
                <g:hiddenField name="version" value="${trackeeInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="emailAddress"><g:message code="trackee.emailAddress.label" default="Email Address" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackeeInstance, field: 'emailAddress', 'errors')}">
                                    <g:textField name="emailAddress" value="${trackeeInstance?.emailAddress}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="trackee.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackeeInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${trackeeInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="specialCode"><g:message code="trackee.specialCode.label" default="Special Code" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackeeInstance, field: 'specialCode', 'errors')}">
                                    <g:textField name="specialCode" value="${trackeeInstance?.specialCode}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="tracks"><g:message code="trackee.tracks.label" default="Tracks" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackeeInstance, field: 'tracks', 'errors')}">
                                    
<ul>
<g:each in="${trackeeInstance?.tracks?}" var="t">
    <li><g:link controller="track" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="track" action="create" params="['trackee.id': trackeeInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'track.label', default: 'Track')])}</g:link>

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
