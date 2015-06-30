<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<u:html title="Личный профиль пользователя">
<c:url var="root" value="/"/>
<DIV id="breadcrumbs"><A href="${root}">Главная</A> :: Проекты</DIV>
<H2>Личный профиль пользователя</H2>
<DIV id="page">
	<DIV class="main-column">
		<c:choose>
			<c:when test="${not empty user}">
				<H3>Учётные данные:</H3>
				<TABLE>
					<tr>
						<td>Псевдоним:</td>
						<td>${user.getName()}</td>
					</tr>
					<tr>
						<td>Фамилия:</td>
						<td>${user.getLastName()}</td>
					</tr>
					<tr>
						<td>Имя:</td>
						<td>${user.getFirstName()}</td>
					</tr>
					<tr>
						<td>Отчество:</td>
						<td>${user.getMiddleName()}</td>
					</tr>
				</TABLE>
				<H3>Контакты:</H3>
				<TABLE>
					<c:forEach items="${user.getContacts()}" var="contact">
						<tr>
							<td>${contact.getType().getName()}:</td>
							<td>${contact.getName()}</td>
						</tr>
					</c:forEach>
				</TABLE>
			</c:when>
			<c:otherwise>
				<H3>Требуется авторизация</H3>
			</c:otherwise>
		</c:choose>
		<FORM action="/pm/edit.html">
			<BUTTON type="submit">Редактировать</BUTTON>
		</FORM>
	</DIV>
	<DIV class="side-column">
		<H3>Роли пользователя:</H3>
		<%--c:forEach items="${user.skypes}" var="skype">
			<tr>
				<td>project name</td>
				<td>
					<c:forEach items="${user.skypes}" var="skype">
						<tr>
							project role
						</tr>
					</c:forEach>
				<td>
			</tr>
		</c:forEach--%>
		<%--tr>
			<td>Администратор</td>
			<td>${isAdmin}</td>
		</tr--%>
	</DIV>
</DIV>
</u:html>
