

<%@ page import="com.certotrack.Track" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'track.label', default: 'Track')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${trackInstance}">
            <div class="errors">
                <g:renderErrors bean="${trackInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="testEmail" >
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
                                    <g:textField name="createdDate" value="${trackInstance?.createdDate}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="track.name.label" default="Subject" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackInstance, field: 'name', 'errors')}">
                                    <g:textField name="subject" value="${trackInstance?.subject}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="track.name.label" default="Body" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: trackInstance, field: 'body', 'errors')}">
                                    <g:textField name="body" value=""/>
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="testEmail" class="save" value="${message(code: 'default.button.testemail.label', default: 'Test Email')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
