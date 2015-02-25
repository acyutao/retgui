var DropzoneUpload = function() {

	return {
		//main function to initiate the module
        init: function () {  

            Dropzone.options.myDropzone = {
                init: function() {
                    this.on("addedfile", function(file) {
                        // Create the remove button
                        var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block red'><i class='fa fa-times'></i> Remove file</button>");
                        
                        // Capture the Dropzone instance as closure.
                        var _this = this;
                        
                        // 文件名编码
                        var fileName = encodeURI(file.name);
                        fileName = encodeURI(fileName);

                        // Listen to the click event
                        removeButton.addEventListener("click", function(e) {
                          // Make sure the button click doesn't submit the form:
                          e.preventDefault();
                          e.stopPropagation();

                          // Remove the file preview.
                          _this.removeFile(file);
                          // If you want to the delete the file on the server as well,
                          // you can do the AJAX request here.
                          $.ajax({
                        	  url: "/retgui/fileName/" + fileName + "/remove",
                        	  type: 'DELETE'
                          });
                        });

                        // Add the button to the file preview element.
                        file.previewElement.appendChild(removeButton);
                        
                        
                        //创建跳转到detail页面的连接                        
                        var detailUrl = "/retgui/detail/" + fileName + "/view";
                        // Create the remove button
                        var detailButton = Dropzone.createElement("<a href=\" " + detailUrl +"\" class='btn btn-sm btn-block blue'><i class='fa fa-search'></i> File Detail </a>");
                        // Add the button to the file preview element.
                        file.previewElement.appendChild(detailButton);
                    });
                    
                    // Capture the Dropzone instance as closure.
                    var _this = this;
                    // 得到所有的文件
                    $.get("/retgui/alluploadfiles", function(data){
                    	for(var i=0; i< data.length; i++){
                    		var mockFile = { name: data[i].fileName, size: data[i].fileSize };
                    		_this.emit("addedfile", mockFile);
                    	}
                    });
                    
                    // 提示错误信息
                    this.on("error", function(file, errorMessage, XHR) {
                    	console.log(errorMessage);
                    	$(file.previewElement.children[4].children[0]).text(errorMessage.globalMessage);
                    });
                },
                
                // 其他的属性
                maxFiles: 1000,
                maxFilesize: 700,
                uploadMultiple: true
            };
            
        }
	};
}();