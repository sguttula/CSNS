<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script>
$(function() {
    $("#tabs").tabs({
        cache: false
    });
});
</script>

<ul id="title">
<li><a class="bc" href="search">Users</a></li>
<li>${user.name} [<a href="edit?id=${user.id}">Edit</a>]</li>
</ul>

<div id="tabs">
<ul>
  <li><a href="#account">Account</a></li>
  <li><a href="standings">Standings</a></li>
  <li><a href="coursework">Course Work</a></li>
  <li><a href="advisement">Advisement</a></li>
</ul>
<div id="account">
<table class="general">
  <tr>
    <th>Name</th>
    <td>${user.firstName} ${user.middleName} ${user.lastName}</td>
  </tr>
  <tr>
    <th>Gender</th>
    <td>
      <c:if test="${user.gender == 'M'}">Male</c:if>
      <c:if test="${user.gender == 'F'}">Female</c:if>
    </td>
  </tr>
  <tr>
    <th>Birthday</th>
    <td><fmt:formatDate pattern="MM/dd/yyyy" value="${user.birthday}" /></td>
  </tr>
  <tr>
    <th>CIN</th>
    <td>
      <c:if test="${! user.cinEncrypted}">${user.cin}</c:if>
      <c:if test="${user.cinEncrypted}">(encrypted)</c:if>
    </td>
  </tr>
  <tr>
    <th>Username</th>
    <td>${user.username}</td>
  </tr>
  <tr>
    <th>Primary Email</th>
    <td>${user.primaryEmail}</td>
  </tr>
  <tr>
    <th>Secondary Email</th>
    <td>${user.secondaryEmail}</td>
  </tr>
  <tr>
    <th>Cell Phone</th>
    <td>${user.cellPhone}</td>
  </tr>
  <tr>
    <th>Home Phone</th>
    <td>${user.homePhone}</td>
  </tr>
  <tr>
    <th>Office Phone</th>
    <td>${user.officePhone}</td>
  </tr>
  <tr>
    <th>Address</th>
    <td>${user.address}</td>
  </tr>
</table>
</div> <!-- account -->
</div> <!-- tabs -->
