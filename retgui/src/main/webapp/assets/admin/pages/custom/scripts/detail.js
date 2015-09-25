var Detail = function(fileName) {

	var queryDetail = function(fileName, str) {
		Pace.track(function() {
					$.ajax({
								url : "/retgui/detail/" + fileName + "/view",
								type : "POST",
								data : str,
								dataType : 'json',
								contentType : 'application/json',
								traditional : true,
								success : function(data) {

									$("div#content").html("");

									var optionstring = "";
									for ( var i in data) {
										var jsonAll = eval(data[i]);
										var jsonObj = jsonAll.sequentialRecords;
										for ( var j = 0; j < jsonObj.length; j++) {
											var jj = jsonObj[j];
											var vv = jj.elementMap;
											var uu = jj.keys;
											var size = 0;
											optionstring += "<div class=\"note note-success\">"
											if (uu[0] == "RCID") {
												optionstring += "<h4 class=\"block\">"
														+ "IT0"
														+ vv['RCID']
														+ "</h4>";
											}
											optionstring += "<p>";
											for ( var m = 0; m < uu.length; m++) {
												var mm = vv[uu[m]];
												var mmlen = mm.trim().length;
												if (mmlen == 0) {
													mmlen = 1;
												}
												if (size + mmlen
														+ uu[m].length > 120) {
													size = 0;
													optionstring += "</p>";
													optionstring += "<p>";
												}

												optionstring += "<span class=\"label label-default\"><strong>"
														+ mm
														+ "</strong><span class=\"badge badge-primary\">"
														+ uu[m]
														+ "</span></span>";
												size += mmlen
														+ uu[m].length;

											}
											optionstring += "</p>";
											optionstring += "</div>";
										}

									}
									$("div#content").html(optionstring);

								},
								error : function(response) {
									alert(response.status);
									alert(response.readyState);
								}

							});
				});
	}

	var queryButton = function(fileName) {
		$("#input-trnn").keyup(function (event) {

            if (event.keyCode == "13") {
       　　　　　	//已经写好的点击按钮所执行的js方法
　　　　　　　　　//Fun_Submit();
            	queryDetail(fileName, $("#input-trnn").val());
            }

        });
		
		$("#queryButton").bind("click", function() {
			queryDetail(fileName, $("#input-trnn").val());
		});
		
	}

	return {
		init : function(fileName) {
			
			queryButton(fileName);

			queryDetail(fileName, '000001');
			
		}

	};
}();