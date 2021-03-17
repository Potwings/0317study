<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload Page</title>
</head>
<body>

<input type="file" name="uploadFile" multiple="multiple">

<button class="uploadBtn">업로드</button>

<ul class="uploadUL">

</ul>

<script>

const input = document.querySelector("input[name='uploadFile']")

const uploadBtn = document.querySelector(".uploadBtn")

const uploadUL = document.querySelector(".uploadUL")

uploadBtn.addEventListener("click",function(){
	
	var formData = new FormData();
	
	var files = input.files
	
	for(let i = 0; i< files.length; i++ ){
		
		var file = files[i];
		
		formData.append("files",file);
		
	}
	
	fetch("/upload",{method:'post', body:formData})
	.then(res=>res.json()).then(result => {
		
		for(let i=0; i< result.length; i++){
			
			uploadUL.innerHTML += "<li><image src='/view/"+result[i].fileName+"/"+result[i].uuid+"'></li>";
			
		}
	})
	
	
	
	
},false)


</script>

</body>
</html>