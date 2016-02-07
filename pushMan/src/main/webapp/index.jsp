<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html>
<sql:query var="rs" dataSource="jdbc/test2016">
select MSG_SEQ, MSG_ETC1 from MSG_LOG_201601
</sql:query>

<html>
  <head>
    <title>DB Test</title>
  </head>
  <body>

  <h2>Results</h2>

<c:forEach var="row" items="${rs.rows}">
    No ${row.MSG_SEQ}<br/>
    Con: ${row.MSG_ETC1}<br/>
</c:forEach>

  </body>
</html>