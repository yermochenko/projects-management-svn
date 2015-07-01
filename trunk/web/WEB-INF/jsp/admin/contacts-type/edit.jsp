<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<u:html title="Типы контактов" message="${message}">
<c:url var="root" value="/" />
<DIV id="breadcrumbs">
	<A href="${root}">Главная</A> :: Типы контактов
</DIV>
<c:choose>
	<c:when test="${not empty type}">
		<H2>Редактирование типа контакта</H2>
	</c:when>
	<c:otherwise>
		<H2>Создание типа контакта</H2>
	</c:otherwise>
</c:choose>
<DIV id="page">
	<DIV class="single-column">
		<c:choose>
			<c:when test="${not empty type}">
				<H3>Тип контактов «${type.getName()}»</H3>
			</c:when>
			<c:otherwise>
				<H3>Новый тип контактов</H3>
			</c:otherwise>
		</c:choose>
		<FORM action="save.html" method="post">
			<c:if test="${not empty type}">
				<INPUT type="hidden" name="identity" value="${type.getId()}">
			</c:if>
			<LABEL for="name">Название типа контакта:</LABEL> 
			<INPUT type="text" id="name" name="name" value="${type.getName()}"> 
			<LABEL for="regexp">Регулярное выражение для проверки корректности значения значения контакта:</LABEL> 
			<INPUT type="text" id="regexp" name="regexp" value="${type.getRegexp()}">
			<BUTTON type="submit">Сохранить</BUTTON>
			<c:if test="${not empty type}">
				<BUTTON type="button" onclick="submitFormById('delete-form')">Удалить</BUTTON>
			</c:if>
			<BUTTON type="reset">Сбросить</BUTTON>
			<BUTTON type="button" onclick="submitFormById('cancel-form')">Отменить</BUTTON>
		</FORM>
		<c:if test="${not empty type}">
			<FORM id="delete-form" action="delete.html"	method="post"
				onsubmit="return confirmation(this, 'Вы уверены, что хотите удалить тип контактов «${type.getName()}»?');">
			<INPUT type="hidden" name="identity" value="${type.getId()}">
			</FORM>
		</c:if>		
		<FORM id="cancel-form" action="list.html"></FORM>
	</DIV>
</DIV>
</u:html>