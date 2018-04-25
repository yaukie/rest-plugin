<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" type="text/css" href='<%=request.getContextPath()%>/resources/static/bootstrap.min.css' />
 <script  src="<%=request.getContextPath()%>/resources/static/jquery.min.js" />
<script src="<%=request.getContextPath()%>/resources/static/bootstrap.min.js"/>
<script src="<%=request.getContextPath()%>/resources/static/handlebars.min.js"/>

<script>
$(function(){
 $.ajax({
	 type: 'get',
     url: 'http://localhost:8080/inspur/ws/rest/getAllCustomers',
     dataType:'json',
     success:function(data){
    	 $("#hello").html(data.name);
     }
 })
}); 
</script>
<body>
	<div class="container">
    <div class="page-header">
        <h1>Hello</h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <div id="hello"></div>
        </div>
    </div>
</div>
</body>
