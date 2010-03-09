window.top.$.setupJMPopups({
					screenLockerBackground: "#003366",
					screenLockerOpacity: "0.7"
				});

				function openStaticPopup() {
					window.top.$.openPopupLayer({
						name: "myStaticPopup",
						width: 550,
						target: "myHiddenDiv"
					});
				}

				function openAjaxPopup() {
					window.top.$.openPopupLayer({
						name: "mySecondPopup",
						width: 300,
						url: "ajax_example.html"
					});
				}
				function openAjaxIframePopup() {
					window.top.$.openPopupLayer({
						name: "my3Popup",
						width: 300,
						url: "ajax_example-iframe.html",
						success : function(){
						
					}
					});
				}