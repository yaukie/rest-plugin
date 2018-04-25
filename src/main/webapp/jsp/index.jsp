<%@ page pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ page isELIgnored="false"%>
<link rel="stylesheet" type="text/css" href='<%=request.getContextPath()%>/static/bootstrap.min.css' />
 <script  src="<%=request.getContextPath()%>/static/jquery.min.js" />
<script src="<%=request.getContextPath()%>/static/bootstrap.min.js"/>
<script src="<%=request.getContextPath()%>/static/handlebars.js"/>
<!-- 自定义模版 -->
<script type="text/x-handlebars-template" id="customer_table_template">
	{{if data}}
			<table class="table table-hover" id="product_table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Product Name</th>
                    <th>Price</th>
                </tr>
            </thead>
		<tbody>
				{{data}}
				<tr>
					<td>{{id}}</td>
					<td>{{name}}</td>
				</tr>
				{{/data}}
		</tbody>
		</table>
{{else}}
<div class="alert alert-warning">没有任何数据!</div>
{{/if}}
</script>

<script>
$(function(){
 $.ajax({
	 type: 'get',
     url: '/inspur/ws/rest/getAllCustomers',
     dataType:'json',
     success:function(data){
   	var html="<table class=\"table table-hover\" id=\"product_table\">"
   		html+="<thead>"
   		html+="  <tr>"
   		html+="  <th>客户编号</th>"
   		html+="  <th>客户名称</th>"
   		 html +=" </tr>"
   		 html +="</thead>"
   		 html +="<tbody>"
   		 alert(data);
   		 $.each(data,function(i,v){
   			 html+="<tr>"
   			 html+="<td>"+data[i].id+"</td>"
   			 html+="<td>"+data[i].name+"</td>"
   			html+="</tr>"
   		 });
	html +="</tbody>"
	html +="</table>"
    	 $("#customer").html(html);
     }
 })
}); 
</script>
<body>
	<div class="container">
    <div class="page-header">
        <h1>Customer</h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">Customer List</div>
        <div class="panel-body">
            <div id="customer"></div>
        </div>
    </div>
</div>
</body>
