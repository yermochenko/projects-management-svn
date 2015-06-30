<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="by.vsu.pms.domain.project.employee.Role"%>
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
						<td>${user.login}</td>
					</tr>
					<tr>
						<td>Фамилия:</td>
						<td>${user.secondname}</td>
					</tr>
					<tr>
						<td>Имя:</td>
						<td>${user.name}</td>
					</tr>
					<tr>
						<td>Отчество:</td>
						<td>${user.patronomic}</td>
					</tr>
					<FORM action="#">
						<BUTTON type="submit">Отменить</BUTTON>
					</FORM>
				</TABLE>
				<H3>Контакты:</H3>
				<TABLE>
					<c:forEach items="${user.phones}" var="phone">
						<tr>
							<td>Телефон:</td>
							<td>${phone}</td>
							<FORM action="#">
								<BUTTON type="submit">Удалить</BUTTON>
							</FORM>
						</tr>
					</c:forEach>
					<c:forEach items="${user.mails}" var="mail" >
						<tr>
							<td>Адрес электронной почты:</td>
							<td>${mail}</td>
						</tr>
					</c:forEach>
					<c:forEach items="${user.skypes}" var="skype">
						<tr>
							<td>Имя пользователя в Skype:</td>
							<td>${skype}</td>
						</tr>
					</c:forEach>
				</TABLE>
				<H3>Добавление нового контакта:</H3>
				<TABLE>
					<form action="#">
						<h4>Тип контакта:</h4>
						<select>
							<c:forEach items="${contacts_type}" var="contact_type">
								<option>${contact_type}</option>
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
