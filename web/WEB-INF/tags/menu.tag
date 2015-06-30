<%@tag language="java" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<DIV id="header">
<H1>Управление<BR>Проектами</H1>
<c:if test="${not empty authorizedUser}">
	<c:url var="arrowImageUrl" value="/img/arrow-white-bottom.gif"/>
	<c:if test="${not empty userProjects}">
		<UL class="left">
			<LI class="item">
				<A href="#" class="droplink">${currentProject.name} <IMG class="arrow" src="${arrowImageUrl}"></A>
				<OL class="drop">
					<c:url var="mainUrl" value="/index.html"/>
					<c:forEach var="userProject" items="${userProjects}">
						<c:if test="${userProject == currentProject}">
							<c:set var="current" value="current"/>
						</c:if>
						<LI>
							<A href="#" class="${current}" onclick="return submitFormById('project-${userProject.identity}')">${userProject.name}</A>
							<FORM action="${mainUrl}" method="post" id="project-${userProject.identity}">
								<INPUT type="hidden" name="identity" value="${userProject.identity}">
							</FORM>
						</LI>
						<c:remove var="current"/>
					</c:forEach>
				</OL>
			</LI>
		</UL>
	</c:if>
	<UL class="right">
		<c:if test="${not empty mainMenu}">
			<c:forEach var="menuItem" items="${mainMenu}">
				<LI class="item">
					<c:choose>
						<c:when test="${not empty menuItem.url}">
							<c:url var="itemUrl" value="${menuItem.url}"/>
							<c:if test="${currentPage == itemUrl}">
								<c:set var="current" value="current"/>
							</c:if>
							<A href="${itemUrl}" class="${current}">${menuItem.name}</A>
							<c:remove var="current"/>
						</c:when>
						<c:otherwise>
							<A href="#" class="droplink">${menuItem.name} <IMG class="arrow" src="${arrowImageUrl}"></A>
							<OL class="drop">
								<c:forEach var="subMenuItem" items="${menuItem.subMenu}">
									<c:url var="itemUrl" value="${subMenuItem.url}"/>
									<c:if test="${currentPage == itemUrl}">
										<c:set var="current" value="current"/>
									</c:if>
									<LI><A href="${itemUrl}" class="${current}">${subMenuItem.name}</A></LI>
									<c:remove var="current"/>
								</c:forEach>
							</OL>
						</c:otherwise>
					</c:choose>
				</LI>
			</c:forEach>
		</c:if>
		<LI class="item">
			<A href="#" class="droplink">${authorizedUser.name} <IMG class="arrow" src="${arrowImageUrl}"></A>
			<OL class="drop">
				<c:url var="itemUrl" value="/profile.html"/>
				<c:if test="${currentPage == itemUrl}">
					<c:set var="current" value="current"/>
				</c:if>
				<LI><A href="${itemUrl}" class="${current}">профиль</A></LI>
				<c:remove var="current"/>
				<c:url var="itemUrl" value="/logout.html"/>
				<LI><A href="${itemUrl}">выход</A></LI>
			</OL>
		</LI>
	</UL>
</c:if>
</DIV>
