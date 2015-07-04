<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>
<u:html title="Пользователи системы" message="${message}">
<c:url var="root" value="/" />
<c:set var="count" value="1" scope="page" />
<DIV id="breadcrumbs">
	<A href="${root}">Главная</A> :: Пользователи
</DIV>
<H2>Пользователи системы</H2>
<DIV id="page">
	<DIV class="main-column">
		<H3>Пользователи группы «${groupName}»</H3>
		<TABLE>
			<TR>
				<TH class="insignificant">№</TH>
				<TH>Псевдоним</TH>
				<TH>Фамилия</TH>
				<TH>Имя</TH>
				<TH>Отчество</TH>
			</TR>
			<c:forEach items="${users}" var="user">
				<TR onclick="submitFormById('form-${user.getId()}')">
					<TD class="insignificant">${count}<c:set var="count"
							value="${count + 1}" scope="page" />
						<FORM id="form-${user.getId()}" action="edit.html" method="post">
							<INPUT type="hidden" name="identity" value="${user.getId()}">
						</FORM>
					</TD>
					<TD>${user.getName()}</TD>
					<TD>${user.getLastName()}</TD>
					<TD>${user.getFirstName()}</TD>
					<TD>${user.getMiddleName()}</TD>
				</TR>
			</c:forEach>

		</TABLE>
		<FORM action="add.html">
			<BUTTON type="submit">Добавить пользователя</BUTTON>
		</FORM>
	</DIV>
	<DIV class="side-column">

        <H3>Группы пользователей</H3>
        <UL class="drop-list">
            <c:forEach items="${usersGroups}" var="group">
                <c:if test="${empty group.getParent()}">
                    <c:choose>
                        <c:when test="${group.getChildren().size()!=0}">
                            <li class="drop"><A href="#" onclick="submitFormById('form-g${group.getId()}')">${group.getName()}</A>
                                <FORM id="form-g${group.getId()}" action="list.html" method="post">
                                    <INPUT type="hidden" name="id" value="${group.getId()}">
                                </FORM>
                                <ul class="drop">
                                    <u:usersGroupsTree usersGroups="${usersGroups}" parentGroup="${group}" />
                                </ul>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li><A href="#" onclick="submitFormById('form-g${group.getId()}')">${group.getName()}</A>
                                <FORM id="form-g${group.getId()}" action="list.html" method="post">
                                    <INPUT type="hidden" name="id" value="${group.getId()}">
                                </FORM>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </c:forEach>
        </UL>
        <form action="#">
            <button type="submit">Редактировать группы</button>
        </form>
	</DIV>
</DIV>
<DIV id="copyright">&copy; Лаборатория компьютерных технологий ВГУ
	имени П.&nbsp;М.&nbsp;Машерова</DIV>
</DIV>
</u:html>