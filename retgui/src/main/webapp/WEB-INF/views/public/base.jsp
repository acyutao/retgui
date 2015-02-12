<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="araf" tagdir="/WEB-INF/tags/araf"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <title>
        RET GUI
    </title>
    
    <tiles:insertAttribute name="meta" />

    <tiles:insertAttribute name="css" />
    
    <tiles:insertAttribute name="page-css" ignore="true" />
    
    <!-- GLOBAL SCRIPT -->
    <tiles:insertAttribute name="script"/>  
    <!-- PAGE SCRIPT -->
    <tiles:insertAttribute name="page-script" ignore="true"/>
    

    <tiles:insertAttribute name="shortcut-icon" ignore="true"/><link rel="shortcut icon" href="<c:url value="/assets/logo1.png"/>" />

</head>
<body class="page-header-fixed" data-accountid="${account.id }">
    
    <!-- BEGIN HEADER -->
    <tiles:insertAttribute name="header"/>
    <!-- END HEADER -->
    
    <div class="clearfix">
    </div>
    
    <!-- BEGIN CONTAINER -->
    <div class="page-container">
	    <!-- BEGIN SIDEBAR -->
	    <tiles:insertAttribute name="sidebar"/>
	    <!-- END SIDEBAR -->
	    
	    <!-- BEGIN CONTENT -->
        <div class="page-content-wrapper">
            <div class="page-content">
                
                <!-- BEGIN STYLE CUSTOMIZER -->
	            <div class="theme-panel hidden-xs hidden-sm">
	                <div class="toggler">
	                </div>
	                <div class="toggler-close">
	                </div>
	                <div class="theme-options">
	                    <div class="theme-option theme-colors clearfix">
	                        <span>
	                             THEME COLOR
	                        </span>
	                        <ul>
	                            <li class="color-black current color-default" data-style="default">
	                            </li>
	                            <li class="color-blue" data-style="blue">
	                            </li>
	                            <li class="color-brown" data-style="brown">
	                            </li>
	                            <li class="color-purple" data-style="purple">
	                            </li>
	                            <li class="color-grey" data-style="grey">
	                            </li>
	                            <li class="color-white color-light" data-style="light">
	                            </li>
	                        </ul>
	                    </div>
	                </div>
	            </div>
	            <!-- END STYLE CUSTOMIZER -->
	            	            
	            <!-- BIGIN PAGE -->
	            <tiles:insertAttribute name="page"/>
	            <!-- END PAGE -->
	        </div>
	    </div>
	    <!-- END CONTENT -->
    </div>
    
    <!-- FOOTER -->
    <tiles:insertAttribute name="footer"/>
    
    <!-- MODAL -->
    <tiles:insertAttribute name="modal" ignore="true"/>

    
    
    
</body>
</html>

