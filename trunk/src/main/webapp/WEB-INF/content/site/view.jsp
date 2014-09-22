<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<c:set var="site" value="${section.site}" />

<script>
$(function(){
    document.title = "${section.course.code} ${section.quarter.shortString}";
});
</script>

<c:if test="${isInstructor}">
<ul id="title">
<li><a href="<c:url value='/section/taught' />" class="bc">Instructor's Home</a></li>
<li>${section.course.code}-${section.number}</li>
<li class="align_right"><a href="<c:url value='${section.siteUrl}/block/list' />"><img title="Edit Blocks"
  alt="[Edit Blocks]" src="<c:url value='/img/icons/bricks.png' />" /></a></li>
<li class="align_right"><a href="<c:url value='${section.siteUrl}/info/edit' />"><img title="Edit Class Info"
  alt="[Edit Class Info]" src="<c:url value='/img/icons/table_edit.png' />" /></a></li>
<li class="align_right"><a href="<c:url value='${section.siteUrl}/syllabus/edit' />"><img title="Edit Syllabus"
  alt="[Edit Syllabus]" src="<c:url value='/img/icons/script_edit.png' />" /></a></li>
</ul>
</c:if>

<div class="site-title">${section.course.code} ${section.course.name}</div>
<div class="site-quarter">${section.quarter}</div>

<table id="site" style="width: 100%;">
<tr>
<!-- sidebar -->
<td id="site-sidebar" colspan="1" rowspan="2" class="shrink">
<ul>
  <c:if test="${not empty section.syllabus}">
  <li><a href="<c:url value='${section.siteUrl}/syllabus' />">Syllabus</a></li>
  </c:if>
  <c:forEach items="${site.blocks}" var="block">
  <li><a href="#b${block.id}">${block.name}</a></li>
  </c:forEach>
</ul>
</td> <!-- end of sidebar -->

<!-- class info -->
<td>
<c:if test="${fn:length(site.infoEntries) > 0}">
<table id="site-class-info" class="general">
  <c:forEach items="${site.infoEntries}" var="infoEntry">
  <tr>
    <th>${infoEntry.name}</th>
    <td>${infoEntry.value}</td>
  </tr>
  </c:forEach>
</table>
</c:if>
</td> <!-- end of class info -->
</tr>

<tr>
<!-- blocks -->
<td id="site-content">
<c:forEach items="${site.blocks}" var="block">
<a id="b${block.id}"></a><div class="site-block">
<div class="site-block-title">${block.name}</div>
<div class="site-block-content">
  <ul>
    <li>This is an item.</li>
    <li>This is another item.</li>
  </ul>
</div>
</div> <!-- end of site-block -->
</c:forEach>
</td> <!-- end of blocks -->
</tr>
</table>
