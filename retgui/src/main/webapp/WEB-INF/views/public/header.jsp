<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglib.jsp"%>

<!-- BEGIN HEADER -->
<div class="header navbar navbar-fixed-top">
    <!-- BEGIN TOP NAVIGATION BAR -->
    <div class="header-inner">
        <!-- BEGIN LOGO -->
        <a class="" href="/documents">
            <img src="<c:url value="/assets/headerlogo-small.png"/>" alt="logo" class="" style="width:25%;height:30%;"/>
        </a>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <img src="<c:url value="/assets/img/menu-toggler.png"/>" alt=""/>
        </a>
        <!-- END RESPONSIVE MENU TOGGLER -->
        <!-- BEGIN TOP NAVIGATION MENU -->
        <ul class="nav navbar-nav pull-right">
            <!-- BEGIN INBOX DROPDOWN -->
            <li class="dropdown" id="header_inbox_bar">
               
            </li>
            <!-- END INBOX DROPDOWN -->
            <!-- BEGIN TODO DROPDOWN -->
            <li class="dropdown" id="header_task_bar">
               
            </li>
            <!-- END TODO DROPDOWN -->
            <!-- BEGIN USER LOGIN DROPDOWN -->
            <li class="dropdown user">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                
                	<c:if test="${ empty user.pic }">
                    	<img alt="" src="<c:url value="/assets/img/avatar1_small.jpg"/>"/>                
                    </c:if>
                    <c:if test="${! empty user.pic }">
                    	<img alt="" src="/user/image" style="width:29px;height:29px;"/>
                    </c:if>
                    <span class="username">
                         ${user.id }
                    </span>
                    <i class="fa fa-angle-down"></i>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="/user">
                            <i class="fa fa-user"></i> 个人信息
                        </a>
                    </li>  
                    
                    <li class="divider">
                    </li>
                    <li>
                        <a href="javascript:;" id="trigger_fullscreen">
                            <i class="fa fa-arrows"></i> 全屏
                        </a>
                    </li>
                    <li>
                        <a href="/logout">
                            <i class="fa fa-lock"></i> 锁屏
                        </a>
                    </li>
                    <li>
                        <a href="/logout">
                            <i class="fa fa-key"></i> 退出
                        </a>
                    </li>
                </ul>
            </li>
            <!-- END USER LOGIN DROPDOWN -->
        </ul>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->