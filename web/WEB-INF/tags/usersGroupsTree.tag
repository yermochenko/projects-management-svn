<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ attribute name="usersGroups" type="java.util.ArrayList"%>
<%@ attribute name="parentGroup" type="by.vsu.mf.ammc.pm.domain.user.UsersGroup"%>
<c:forEach var="group" items="${usersGroups}">
    <c:if test="${not empty group.getParent()}">
        <c:if test="${group.getParent().getId() == parentGroup.getId()}">

            <c:choose>
                <c:when test="${group.getChildren().size()!=0}">
                    <li class="drop"><A href="#" onclick="submitFormById('form-g${group.getId()}')">${group.getName()}</A>
                        <FORM id="form-g${group.getId()}" action="list.html"
                              method="post">
                            <INPUT type="hidden" name="id" value="${group.getId()}">
                        </FORM>
                        <ul class="drop">
                            <tag:usersGroupsTree usersGroups="${usersGroups}" parentGroup="${group}" />
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
    </c:if>
</c:forEach>


