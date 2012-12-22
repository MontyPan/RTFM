> # JSP Container #

Servlet
=======
可以設定 servlet 的 url-mapping 的地方：

* 傳統的 web.xml
* Servlet class 掛 `@WebServlet` （JSP 3.0）
* web-fragment.xml

Tomcat 
------
\7.0.29 之後就不會去參考 web.xml 當中的 `<web-app>` 當中 `version="3.0"` 的設定，一定會去掃 annotation。