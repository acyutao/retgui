<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/public/taglib.jsp"%>

<!-- BEGIN HEADER -->
<div class="page-header navbar navbar-fixed-top">
	<!-- BEGIN HEADER INNER -->
	<div class="page-header-inner">
		<!-- BEGIN LOGO -->
		<div class="page-logo">
			<a href="index.html"> <img
				src="<c:url value="/assets/admin/layout/img/logo.png"/>" alt="logo"
				class="logo-default" />
			</a>
			<div class="menu-toggler sidebar-toggler hide">
				<!-- DOC: Remove the above "hide" to enable the sidebar toggler button on header -->
			</div>
		</div>
		<!-- END LOGO -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<div class="menu-toggler responsive-toggler" data-toggle="collapse"
			data-target=".navbar-collapse"></div>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<div class="top-menu">
			<ul class="nav navbar-nav pull-right">
				<!-- BEGIN NOTIFICATION DROPDOWN -->
				<li class="dropdown dropdown-extended dropdown-notification"
					id="header_notification_bar"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"
					data-hover="dropdown" data-close-others="true"> <i
						class="fa fa-warning"></i> 
				</a>
					<ul class="dropdown-menu">
						<li>
							<p>You have no notifications</p>
						</li>
						<li>
							<ul class="dropdown-menu-list scroller" style="height: 50px;">
							</ul>
						</li>
						<li class="external"><a href="#"> See all notifications <i
								class="m-icon-swapright"></i>
						</a></li>
					</ul></li>
				<!-- END NOTIFICATION DROPDOWN -->
				<!-- BEGIN INBOX DROPDOWN -->
				<li class="dropdown dropdown-extended dropdown-inbox"
					id="header_inbox_bar"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" data-hover="dropdown"
					data-close-others="true"> <i class="fa fa-envelope"></i> 
				</a>
					<ul class="dropdown-menu">
						<li>
							<p>You have no messages</p>
						</li>
						<li>
							<ul class="dropdown-menu-list scroller" style="height: 250px;">
							</ul>
						</li>
						<li class="external"><a href="inbox.html"> See all
								messages <i class="m-icon-swapright"></i>
						</a></li>
					</ul></li>
				<!-- END INBOX DROPDOWN -->
				<!-- BEGIN TODO DROPDOWN -->
				<li class="dropdown dropdown-extended dropdown-tasks"
					id="header_task_bar"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" data-hover="dropdown"
					data-close-others="true"> <i class="fa fa-tasks"></i> 
				</a>
					<ul class="dropdown-menu extended tasks">
						<li>
							<p>You have no tasks</p>
						</li>
						<li>
							<ul class="dropdown-menu-list scroller" style="height: 50px;">
								<li><a href="#"> <span class="task"> <span
											class="desc"> New release v1.0 </span> <span class="percent">
												100% </span>
									</span> <span class="progress"> <span style="width: 100%;"
											class="progress-bar progress-bar-success" aria-valuenow="40"
											aria-valuemin="0" aria-valuemax="100"> <span
												class="sr-only"> 40% Complete </span>
										</span>
									</span>
								</a></li>
								
							</ul>
						</li>
						<li class="external"><a href="#"> See all tasks <i
								class="m-icon-swapright"></i>
						</a></li>
					</ul></li>
				<!-- END TODO DROPDOWN -->
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown dropdown-user"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"
					data-hover="dropdown" data-close-others="true"> <img alt=""
						class="img-circle"
						src="<c:url value="/assets/admin/layout/img/avatar3_small.jpg"/>" /> <span
						class="username"> RET GUI </span> <i class="fa fa-angle-down"></i>
				</a>
					<ul class="dropdown-menu">
						<li><a href="extra_profile.html"> <i class="fa fa-user"></i>
								My Profile
						</a></li>
						<li><a href="page_calendar.html"> <i
								class="fa fa-calendar"></i> My Calendar
						</a></li>
						<li><a href="inbox.html"> <i class="fa fa-envelope"></i>
								My Inbox <span class="badge badge-danger"> 3 </span>
						</a></li>
						<li><a href="#"> <i class="fa fa-tasks"></i> My Tasks <span
								class="badge badge-success"> 7 </span>
						</a></li>
						<li class="divider"></li>
						<li><a href="extra_lock.html"> <i class="fa fa-lock"></i>
								Lock Screen
						</a></li>
						<li><a href="login.html"> <i class="fa fa-key"></i> Log
								Out
						</a></li>
					</ul></li>
				<!-- END USER LOGIN DROPDOWN -->
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
		</div>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END HEADER INNER -->
</div>
<!-- END HEADER -->