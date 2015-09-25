<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglib.jsp"%>

<!-- BEGIN PAGE HEADER-->
<div class="row">
	<div class="col-md-12">
		<!-- BEGIN PAGE TITLE & BREADCRUMB-->


		<!-- END PAGE TITLE & BREADCRUMB-->
		<!-- END PAGE TITLE & BREADCRUMB-->
	</div>
</div>
<!-- END PAGE HEADER-->
<!-- BEGIN PAGE CONTENT-->
<div class="row">
	<div class="col-md-12">

		<div class="portlet box blue-steel">
			<div class="portlet-title">
				<div class="caption">
					<i class="glyphicon glyphicon-file"></i> ${fileName}
				</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"> </a> <a
						href="#portlet-config" data-toggle="modal" class="config"> </a> <a
						href="javascript:;" class="reload"> </a>
				</div>
			</div>
			<div class="portlet-body" id="headerContent">
				<a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div style="white-space:pre">${sped}</div> <span class="badge badge-success">SPED</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div style="white-space:pre">${rpsi}</div> <span class="badge badge-success">RPSI</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div style="white-space:pre">${revn}</div> <span class="badge badge-success">REVN</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div style="white-space:pre">${tpst}</div> <span class="badge badge-success">TPST</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div style="white-space:pre">${prda}</div> <span class="badge badge-success">PRDA</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div style="white-space:pre">${time}</div> <span class="badge badge-success">TIME</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div style="white-space:pre">${isoc}</div> <span class="badge badge-success">ISOC</span>
				</a><a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div style="white-space:pre">${ftyp}</div> <span class="badge badge-success">FTYP</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div style="white-space:pre">${ftsn}</div> <span class="badge badge-success">FTSN</span>
				</a>
			</div>
		</div>
	</div>
</div>

<div class="well bg-grey-steel">
	<div class="row search-form-default">
		<div class="col-md-12">
			<form class="form-inline" action="#">
				<div class="input-group">
					<div class="input-cont">
						<input id="input-trnn" type="text"
							placeholder="Search...输入TRNN或者document number"
							class="form-control" />
					</div>
					<span class="input-group-btn">
						<button type="submit" class="btn green" id="queryButton">
							Search &nbsp; <i class="m-icon-swapright m-icon-white"></i>
						</button>
					</span>
				</div>
			</form>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12"></div>
</div>

<div class="row">
	<div class="col-md-12" id="content">
		<div class="note note-success"></div>
	</div>
</div>

<!-- END PAGE CONTENT-->
