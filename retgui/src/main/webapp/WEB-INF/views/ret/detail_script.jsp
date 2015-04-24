<%@ taglib prefix="araf" tagdir="/WEB-INF/tags/araf"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<araf:script url="assets/global/scripts/metronic.js" />
<araf:script url="assets/admin/layout/scripts/layout.js" />
<araf:script url="assets/admin/pages/custom/scripts/upload.js" />
<araf:script url="assets/admin/pages/custom/scripts/detail.js" />
<script>
	jQuery(document).ready(function() {
		// initiate layout and plugins
		Metronic.init(); // init metronic core components
		Layout.init(); // init current layout
		Detail.init("${fileName}");
	});
</script>
<!-- END PAGE LEVEL SCRIPTS -->
