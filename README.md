# ImoocMall (Not Finish yet... Keep updating!)
### ImoocMall is a back-end web application for serving a food shopping mall (e-commerce platform).<br>
This application will implement the API document:幕幕生鮮接口文檔<br>
Link: https://shimo.im/docs/K3WhpQ33RcqvkdyD/read

### Frameworks
1. SpringBoot:Provide an auto-configuration application for Spring framework.
2. SpringMVC:Provide web service.
3. MySQL:Provide database support.

### Features
1. RestResponse:Design a class named ApiRestResponse to present REST response.Including response code,msg,and data.
2. GlobalExceptionHandler:Use SpringMVC handler to catch all exceptions in system,and handle all exceptions to generating uniform rest response.
3. LoginCheck:Use filter to check the session information in client request.If there is no user information in session and want to access the url associated with user, the filter will reject that request,and require user to login.
4. AdminFilter:Use filter to check user has the admin permission or not.If user try to access to admin url without admin permission, the filter will reject that request.
5. PasswordEncryption:Provide password encryption with MD5 algorithm.
6. Redis Caching:Use Redis to cache query data.
7. APIDocument:Use swagger to show the REST api document in a web page.
8. WebLog:Use aspect to record all the requests and responses for collecting necessary information.<br>
   For Request:Record request url and client ip.<br>
   For Response:Record response status,response data.
9. SystemLog:Use log4j to recording system run-time status.Including error,exception,setting...etc.

### TestTool
1. Postman:Send request to web service and receive the REST response.

### [Warming] This project is not finish yet. I will keep updating!
