<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<sql:query var="rs" dataSource="jdbc/pms">
select ID, BIZ_ID from TB_SEND_QUE_LOG
</sql:query>

<html>
  <head>
    <title>DB Test</title>
  </head>
  <body>

  <h2>Results</h2>

<c:forEach var="row" items="${rs.rows}">
    No: ${row.ID}<br/>
    Con: ${row.BIZ_ID}<br/>
</c:forEach>

  </body>
</html>