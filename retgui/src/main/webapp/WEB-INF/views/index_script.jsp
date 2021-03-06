<%@ taglib prefix="araf" tagdir="/WEB-INF/tags/araf"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<araf:script url="assets/global/plugins/dropzone/dropzone.js" />
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<araf:script url="assets/global/scripts/metronic.js" />
<araf:script url="assets/admin/layout/scripts/layout.js" />
<araf:script url="assets/admin/pages/custom/scripts/upload.js" />
<script>
	jQuery(document).ready(function() {
		// initiate layout and plugins
		Metronic.init(); // init metronic core components
		Layout.init(); // init current layout
		DropzoneUpload.init();
	});
</script>
<!-- END PAGE LEVEL SCRIPTS -->
