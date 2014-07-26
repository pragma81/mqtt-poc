<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<html>
<body>
<h2>Hello World!</h2>
<%
    Logger logger = LoggerFactory.getLogger("localdomain.localhost.jsp");
    System.out.println("Rendering this page appends warn, info, debug and trace messages on logger: <code>" + logger.getName() + "<code>");
    out.println("Append warn, info, debug and trace messages on logger: " + logger.getName());
    logger.warn("index.jsp warn");
    logger.info("index.jsp info");
    logger.debug("index.jsp debug");
    logger.trace("index.jsp trace");
%>
</body>
</html>
