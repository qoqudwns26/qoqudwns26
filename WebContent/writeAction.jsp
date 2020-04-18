<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="bbj.BbjDAO" %>
    <%@ page import="java.io.PrintWriter" %>
    <% request.setCharacterEncoding("UTF-8"); %>
    <jsp:useBean id="bbj" class="bbj.Bbj" scope="page"/>
    <jsp:setProperty name="bbj" property="bbjTitle"/>
    <jsp:setProperty name="bbj" property="bbjContent"/>
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
} else {
if(bbj.getBbjTitle() == null || bbj.getBbjContent() == null) {
	PrintWriter script = response.getWriter();
	script.println("<script>");
	script.println("alert('입력이 안 된 사람이 있습니다.')");
	script.println("history.back()");
	script.println("</script>");
} else {
	BbjDAO bbjDAO = new BbjDAO();
	int result = bbjDAO.write(bbj.getBbjTitle(), userID, bbj.getBbjContent());
	if (result == -1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('글쓰기에 실패했습니다.')");
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


}

%>
</body>
</html>