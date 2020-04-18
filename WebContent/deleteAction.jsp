<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="bbj.BbjDAO" %>
    <%@ page import="bbj.Bbj" %>
    <%@ page import="java.io.PrintWriter" %>
    <% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>

<%
String userID = null;
if(session.getAttribute("userID") != null) {
	userID = (String) session.getAttribute("userID");
}
if(userID == null) {
	PrintWriter script = response.getWriter();
	script.println("<script>");
	script.println("alert('로그인을 하세요')");
	script.println("location.href = 'login.jsp'");
	script.println("</script>");
} 
int bbjID = 0;
if (request.getParameter("bbjID") != null) {
	bbjID = Integer.parseInt(request.getParameter("bbjID"));
}
if (bbjID == 0) {
	PrintWriter script = response.getWriter();
	script.println("<script>");
	script.println("alert('유효하지 않은 글입니다.')");
	script.println("location.href = 'bbj.jsp'");
	script.println("</script>");
}
Bbj bbj = new BbjDAO().getBbj(bbjID);
if (!userID.equals(bbj.getUserID())) {
	PrintWriter script = response.getWriter();
	script.println("<script>");
	script.println("alert('권한이 없습니다')");
	script.println("location.href = 'bbj.jsp'");
	script.println("</script>");
} else {
	BbjDAO bbjDAO = new BbjDAO();
	int result = bbjDAO.delete(bbjID);
	if (result == -1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('글 삭제에 실패했습니다.')");
		script.println("history.back()");
		script.println("</script>");

	}
	else {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'bbj.jsp'");
		script.println("</script>");
	}
}

%>
</body>
</html>