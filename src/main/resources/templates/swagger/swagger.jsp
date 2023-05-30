<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <c:set var="path">${fn:substring(pageContext.request.requestURL, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/</c:set>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Swagger UI</title>
        <link rel="stylesheet" type="text/css" href="${path}swagger/css/swagger-ui.css" >
        <link rel="icon" type="image/png" href="${path}swagger/img/favicon-32x32.png" sizes="32x32" />
        <link rel="icon" type="image/png" href="${path}swagger/img/favicon-16x16.png" sizes="16x16" />
        <style>
            html {
                box-sizing  : border-box;
                overflow    : -moz-scrollbars-vertical;
                overflow-y  : scroll;
            }
            *, *:before, *:after {
                box-sizing: inherit;
            }
            body {
                margin:0;
                background: #fafafa;
            }
        </style>
    </head>
    <body>
                <div id="swagger-ui"></div>

    <script src="${path}swagger/js/swagger-ui-bundle.js"> </script>
    <script src="${path}swagger/js/swagger-ui-standalone-preset.js"> </script>
    <script>
    window.onload = function() {
      const ui = SwaggerUIBundle({
            url             : "${path}api/swagger.json",
            dom_id          : '#swagger-ui',
            deepLinking     : true,
            presets         : [
                SwaggerUIBundle.presets.apis,
                SwaggerUIStandalonePreset
            ],
            plugins: [
                SwaggerUIBundle.plugins.DownloadUrl
            ],
            layout: "StandaloneLayout"
      });

      window.ui = ui;
    };
  </script>
    </body>
</html>
