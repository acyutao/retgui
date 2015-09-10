<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglib.jsp"%>
    
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
    <div class="page-sidebar navbar-collapse collapse">
        <!-- add "navbar-no-scroll" class to disable the scrolling of the sidebar menu -->
        <!-- BEGIN SIDEBAR MENU -->
        <ul class="page-sidebar-menu page-sidebar-menu-closed" data-auto-scroll="true" data-slide-speed="200">
            <li class="sidebar-toggler-wrapper">
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler hidden-phone">
                </div>
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
            </li>
            <li class="sidebar-search-wrapper">
                <!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
                <form class="sidebar-search" action="/documents" method="GET">
                    <div class="form-container">
                        <div class="input-box">
                            <a href="javascript:;" class="remove">
                            </a>
                            
                        </div>
                    </div>
                </form>
                <!-- END RESPONSIVE QUICK SEARCH FORM -->
            </li>
            <li class="start active"  id="documents">
                <a href="<c:url value="/index"/>">
                    <i class="fa fa-home"></i>
                    <span class="title">
                    	文档列表
                    </span>
                    <span class="selected">
                    </span>
                </a>
            </li>
            
            
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
</div>
<!-- END SIDEBAR -->

<script>

$(document).ready(function() {
	
	$('.page-sidebar-wrapper li').removeClass("active");
	
	$('#${menu}').addClass("active");
	$('#${menu}').parent().closest("li").addClass("active");
	
})


</script>