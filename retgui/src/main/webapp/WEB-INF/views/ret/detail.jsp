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
					<div>${sped}</div> <span class="badge badge-success">SPED</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div>${rpsi}</div> <span class="badge badge-success">RPSI</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div>${revn}</div> <span class="badge badge-success">REVN</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div>${tpst}</div> <span class="badge badge-success">TPST</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div>${prda}</div> <span class="badge badge-success">PRDA</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div>${time}</div> <span class="badge badge-success">TIME</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div>${isoc}</div> <span class="badge badge-success">ISOC</span>
				</a><a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div>${ftyp}</div> <span class="badge badge-success">FTYP</span>
				</a> <a href="#" class="icon-btn"> <i class="fa fa-group"></i>
					<div>${ftsn}</div> <span class="badge badge-success">FTSN</span>
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
						<input id="input-trnn" type="text" placeholder="Search...输入TRNN或者document number" class="form-control" />
					</div>
					<span class="input-group-btn">
						<button type="button" class="btn green" id="queryButton">
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
		<div class="note note-success">
			<h4 class="block">IT02</h4>

			<p>
				<span class="label label-default"><strong>2</strong><span
					class="badge badge-primary">TRNN</span></span>
					 <span
					class="label label-default"><strong>000001</strong><span
					class="badge badge-primary">TRNN</span></span> <span
					class="label label-default"><strong>02361483 F
						140304D</strong><span class="badge badge-primary">TDNR</span></span> <span
					class="label label-default"><strong>9540034600</strong><span
					class="badge badge-primary">CDGT</span></span>
			</p>
			<p>
				<span class="label label-default"><strong>857 </strong><span
					class="badge badge-primary">TCNR</span></span> <span
					class="label label-default"><strong>5TA</strong><span
					class="badge badge-primary">TACN</span></span> <span
					class="label label-default"><strong>5TASF00</strong><span
					class="badge badge-primary">TNNR</span></span> <span
					class="label label-default"><strong>110304332137</strong><span
					class="badge badge-primary">TRNN</span></span> <span
					class="label label-default"><strong> 6954 2 </strong><span
					class="badge badge-primary">TRNN</span></span> <span
					class="label label-default"><strong>PAYMAN/KAYVAN</strong><span
					class="badge badge-primary">TRNN</span></span> <span
					class="label label-default"><strong> DARYOUSH MR A</strong><span
					class="badge badge-primary">TRNN</span></span> <span
					class="label label-default"><strong>000000000000
						000000000000 </strong><span class="badge badge-primary">TRNN</span></span> <span
					class="label label-default"><strong>000000000000
						0000 AU</strong><span class="badge badge-primary">TRNN</span></span>



			</p>
		</div>
		<div class="note note-info">
			<h4 class="block">IT03</h4>
			<p>300000210006184808203590 50000 00000 00000 00000 00000 00000
				0140220</p>
		</div>
		<div class="note note-danger">
			<h4 class="block">IT04</h4>
			<p>40000037BYRJ5 BNESIN / T1305055 AUD 955.00 AUD365.60YQAUD
				23.20YQAUD160.44XTAUD 1504.24 7906 9999WS 0239029002390290</p>
		</div>
		<div class="note note-warning">
			<h4 class="block">IT05</h4>
			<p>500000300000000000 YQ 00000036560YQ 00000002320WY
				0000000471800000150424AUD200000000000 AU 00000005500WG 00000000358WG
				00000000358 0000000000000000 0000000000000000 0000000000000000
				00000000000</p>
		</div>
	</div>
</div>

<!-- END PAGE CONTENT-->
