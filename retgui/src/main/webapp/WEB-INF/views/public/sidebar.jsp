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
                <a href="/documents">
                    <i class="fa fa-home"></i>
                    <span class="title">
                    	文档列表
                    </span>
                    <span class="selected">
                    </span>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="fa fa-gears"></i>
                    <span class="title">
                                                管理
                    </span>
                    <span class="arrow ">
                    </span>
                </a>
                <ul class="sub-menu">
                    <sec:authorize access="hasAnyRole('ADMIN', 'USER')">
                    <li id="user">
                        <a href="/user">
                            <i class="fa fa-user"></i>
                                                        个人信息
                        </a>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ADMIN', 'SUPER_ADMIN')">
                    <li id="users">
                        <a href="/users">
                            <i class="fa fa-users"></i>
                                                        用户权限
                        </a>
                    </li>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ADMIN')">
                    <li id="lawRisks">
                        <a href="/lawRisks">
                            <i class="fa fa-ban"></i>
                                                       法律风险维护
                        </a>
                    </li>
                    </sec:authorize>
                </ul>
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