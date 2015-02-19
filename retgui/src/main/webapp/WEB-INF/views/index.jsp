<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglib.jsp"%>

<!-- BEGIN PAGE HEADER-->
<div class="row">
	<div class="col-md-12">
		<!-- BEGIN PAGE TITLE & BREADCRUMB-->
		<h3 class="page-title">
			Dropzone File Upload <small>amazing dropzone file upload
				sample</small>
		</h3>
		<ul class="page-breadcrumb breadcrumb">
			<li class="btn-group">
				<button type="button" class="btn blue dropdown-toggle"
					data-toggle="dropdown" data-hover="dropdown" data-delay="1000"
					data-close-others="true">
					<span>Actions</span><i class="fa fa-angle-down"></i>
				</button>
				<ul class="dropdown-menu pull-right" role="menu">
					<li><a href="#">Action</a></li>
					<li><a href="#">Another action</a></li>
					<li><a href="#">Something else here</a></li>
					<li class="divider"></li>
					<li><a href="#">Separated link</a></li>
				</ul>
			</li>
			<li><i class="fa fa-home"></i> <a href="index.html">Home</a> <i
				class="fa fa-angle-right"></i></li>
			<li><a href="#">Form Stuff</a> <i class="fa fa-angle-right"></i>
			</li>
			<li><a href="#">Dropzone File Upload</a></li>
		</ul>
		<!-- END PAGE TITLE & BREADCRUMB-->
		<!-- END PAGE TITLE & BREADCRUMB-->
	</div>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
	<div class="col-md-12">
		<p>
			<span class="label label-danger"> NOTE: </span> &nbsp; This plugins
			works only on Latest Chrome, Firefox, Safari, Opera & Internet
			Explorer 10.
		</p>
		<form action="<c:url value="/upload"/>" class="dropzone" id="my-dropzone"></form>
	</div>
</div>
<!-- END PAGE CONTENT-->
