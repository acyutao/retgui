var Detail = function(fileName) {

	return {
		init : function(fileName) {
			



			$
					.ajax({
						url : "/retgui/detail/" + fileName + "/view",
						type : "POST",
						data : '{}',
						dataType : 'json',
						contentType : 'application/json',
						traditional : true,
						success : function(data) {
						
							var optionstring = "";

							for ( var i in data) {
								var jsonObj = eval(data[i]);
								for ( var j = 0; j < jsonObj.length; j++) {
									var jj = jsonObj[j];
									var vv = eval(jj.record);
									var size = 0;
									optionstring += "<div class=\"note note-success\">"
									if (vv[0].id == "RCID") {
										optionstring += "<h4 class=\"block\">"
												+ "IT0" + vv[0].value + "</h4>";
									}
									optionstring += "<p>";
									for ( var m = 0; m < vv.length; m++) {
										var mm = vv[m];
										if (size + mm.value.length > 68) {
											size = 0;
											optionstring += "</p>";
											optionstring += "<p>";
										}

										optionstring += "<span class=\"label label-default\"><strong>"
												+ mm.value
												+ "</strong><span class=\"badge badge-primary\">"
												+ mm.id + "</span></span>";
										size += mm.value.length;

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

		}

	};
}();