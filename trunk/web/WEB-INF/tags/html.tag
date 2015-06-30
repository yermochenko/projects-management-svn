<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@tag language="java" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>
<%@attribute name="message" required="false" rtexprvalue="true" type="java.lang.String"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>

<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<TITLE>Управление Проектами - ${title}</TITLE>
	<c:url value="/main.css" var="cssUrl"/>
	<LINK rel="stylesheet" type="text/css" href="${cssUrl}">
	<c:url value="/main.js" var="javascriptUrl"/>
	<SCRIPT type="text/javascript" src="${javascriptUrl}"></SCRIPT>
	<c:url value="/img/arrow-gray-bottom.gif" var="openedItemImageUrl"/>
	<c:url value="/img/arrow-gray-right.gif" var="closedItemImageUrl"/>
	<SCRIPT type="text/javascript">
		openedItemImageUrl = "${openedItemImageUrl}";
		closedItemImageUrl = "${closedItemImageUrl}";
	</SCRIPT>
	<c:if test="${not empty message}">
		<SCRIPT type="text/javascript">
			startMessage = "${message}";
		</SCRIPT>
	</c:if>
</HEAD>
<BODY>
<u:menu/>
<DIV id="wrapper">
<DIV id="content">
	<jsp:doBody/>
	<DIV id="copyright">&copy; Лаборатория компьютерных технологий ВГУ имени П.&nbsp;М.&nbsp;Машерова</DIV>
</DIV>
</DIV>
</BODY>
</HTML>