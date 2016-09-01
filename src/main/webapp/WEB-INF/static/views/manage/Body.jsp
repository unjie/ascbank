<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<jsp:include page="header.jsp" />
		<jsp:include page="leftMenu.jsp"></jsp:include>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
		<jsp:include page="${URIBody}"/>
		</div>
		<jsp:include page="footer.jsp" />
		<jsp:include page="controlSidebar.jsp" />
	</div>
	<!-- ./wrapper -->