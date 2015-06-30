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
				<FORM action="/pm/view.html">
						<BUTTON type="submit">Отменить</BUTTON>
					</FORM>
				<H3>Контакты:</H3>
				<TABLE>
					<c:forEach items="${user.getContacts()}" var="contact">
						<tr>
							<td>${contact.getType().getName()}:</td>
							<td>${contact.getName()}</td>
							<td><FORM action="#">
								<BUTTON type="submit">Удалить</BUTTON>
							</FORM></td>
						</tr>
					</c:forEach>
				</TABLE>
				<H3>Добавление нового контакта:</H3>
				<TABLE>
					<form action="#">
						<h4>Тип контакта:</h4>
						<select>
							<c:forEach items="${types}" var="type">
								<option>${type.getName()}</option>
							</c:forEach>
						</select>
						<h4>Контакт:</h4>
						<input type="text">
						<button type="submit">Добавить</button>
					</form>
				</TABLE>
				<H3>Смена пароля:</H3>
				<TABLE>
					<LABEL>Старый пароль:</LABEL>
					<input type="text">
					<LABEL>Новый пароль:</LABEL>
					<input type="text">
					<LABEL>Новый пароль ещё раз:</LABEL>
					<input type="text">
					<button type="submit">Изменить</button>
				</TABLE>
			</c:when>
			<c:otherwise>
				<H3>Требуется авторизация</H3>
			</c:otherwise>
		</c:choose>
	</DIV>
</DIV>
</u:html>
