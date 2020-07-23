<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%
        String p = (String) request.getAttribute("bi");
        System.out.println(p);
        System.out.println(request.getAttribute("spittleList"));
    %>
    <title>Spittles</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="bi,ming,liang">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <script type="text/javascript">
        var msg="${bi}";
        alert(msg);
    </script>
    <div>${bi}</div>
  </body>
</html>
