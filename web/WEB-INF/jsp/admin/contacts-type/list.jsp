<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<u:html title="Типы контактов" message="${message}">
<c:url var="root" value="/" />
<c:set var="count" value="1" scope="page" />
<DIV id="breadcrumbs">
	<A href="${root}">Главная</A> :: Типы контактов
</DIV>
<H2>Типы контактов</H2>
<DIV id="page">
	<DIV class="single-column">
		<H3>Список типов контактов</H3>
		<TABLE>
			<TR>
				<TH class="insignificant">№</TH>
				<TH>Название</TH>
				<TH>Регулярное выражение</TH>
			</TR>			
			<c:forEach items="${types}" var="type">
				<TR onclick="submitFormById('form-${type.getId()}')">
					<TD class="insignificant">${count}
						<c:set var="count" value="${count + 1}" scope="page" />
						<FORM id="form-${type.getId()}" action="edit.html"
							method="post">
							<INPUT type="hidden" name="identity" value="${type.getId()}">
						</FORM>
					</TD>
					<TD>${type.getName()}</TD>
					<TD><CODE>${type.getRegexp()}</CODE></TD>
				</TR>
			</c:forEach>
		</TABLE>
		<FORM action="add.html" method="post">
			<BUTTON type="submit">Добавить тип контактов</BUTTON>
		</FORM>
	</DIV>
</DIV>
</u:html>