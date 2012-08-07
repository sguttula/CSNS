<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="csns" uri="http://cs.calstatela.edu/csns" %>

<c:set var="section" value="${submission.assignment.section}"/>
<c:set var="assignment" value="${submission.assignment}"/>

<script>
function removeFile( fileId )
{
    var msg = "Are you sure you want to remove this file?";
    if( confirm(msg) )
        window.location.href = "remove?fileId=" + fileId;
}
</script>

<ul id="title">
<li><a class="bc" href="<c:url value='/section/taken' />">${section.quarter}</a></li>
<li><a class="bc" href="<c:url value='/section/taken#section-${section.id}' />">${section.course.code} - ${section.number}</a></li>
<li>${submission.assignment.name}</li>
<c:if test="${assignment.availableAfterDueDate || not assignment.pastDue}">
<li class="align_right"><a href="download?id=${submission.id}"><img title="Download All Files"
  alt="[Download All Files]" src="<c:url value='/img/icons/download.png' />" /></a>
</c:if>
</ul>

<p>Due Date: <csns:dueDate submission="${submission}" /></p>

<c:if test="${not empty assignment.totalPoints}">
<p>Total points: ${assignment.totalPoints}</p>
</c:if>

<c:if test="${not submission.pastDue}">
<form method="post" action="upload" enctype="multipart/form-data"><p>
File: <input type="file" name="uploadedFile" size="50" />
<input type="submit" class="subbutton" value="Upload" />
<input type="hidden" name="id" value="${submission.id}" />
</p></form>
</c:if>

<c:if test="${assignment.availableAfterDueDate || not submission.pastDue}">
<table class="viewtable">
  <tr><th>Name</th><th>Size (B)</th><th>Date</th>
    <c:if test="${not submission.pastDue}"><th></th></c:if>
  </tr>
  <c:forEach items="${submission.files}" var="file">
  <tr>
    <td>${file.name}</td>
    <td>${file.size}</td>
    <td class="duedate"><fmt:formatDate value="${file.date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    <c:if test="${not submission.pastDue}">
    <td class="action">
      <a href="javascript:removeFile(${file.id})"><img alt="[Remove File]"
         title="Remove File" src="<c:url value='/img/icons/script_delete.png'/>" /></a>
    </td>
    </c:if>
  </tr>
  </c:forEach>
</table>
</c:if>

<c:if test="${submission.gradeMailed}">
<p><b>Grade: ${submission.grade}</b></p>
<p><b>Comments:</b></p>
<p>${submission.comments}</p>
</c:if>