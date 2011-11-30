<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>


</head>

<body>
 <%
        out.print(request.getRequestURL().toString());
        String redirectURL = request.getRequestURL().toString() + "walled/medicalCase/search/idaMedicalCaseSearch.faces";
        response.sendRedirect(redirectURL);
    %>

</body>

</html>
