<%@ taglib prefix="araf" tagdir="/WEB-INF/tags/araf"%>

<!-- BEGIN PAGE LEVEL STYLES -->
<araf:stylesheet url="assets/plugins/select2/select2.css"/>
<araf:stylesheet url="assets/plugins/select2/select2-metronic.css"/>
<araf:stylesheet url="assets/plugins/data-tables/DT_bootstrap.css"/>
<araf:stylesheet url="assets/plugins/bootstrap-datepicker/css/datepicker.css"/>
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<araf:script url="assets/plugins/select2/select2.min.js"/>
<araf:script url="assets/plugins/data-tables/jquery.dataTables.js"/>
<araf:script url="assets/plugins/data-tables/DT_bootstrap.js"/>
<araf:script url="assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"/>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<araf:script url="assets/scripts/core/app.js"/>
<araf:script url="assets/scripts/core/datatable.js"/>

<araf:script url="assets/plugins/select2/select2.min.js"/>

<script>
jQuery(document).ready(function() {    
   //App.init(); // initlayout and core plugins  加上之后会报错，菜单展开关闭
   Custom.init();
   Custom.initIntro();
   
});
</script>
