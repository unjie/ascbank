<jsp:include page="../debris/Head.jsp"/>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="${__STATIC__}/index2.html"><b>Admin</b>LTE</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">Sign in to start your session<br/><sf:errors path="*" /></p>

    <form action="${__ROOT__}/user/login" method="POST">
      <div class="form-group has-feedback">
        <input type="text" name="username" class="form-control" placeholder="Username">
        <span class="fa  fa-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="password" class="form-control" placeholder="Password">
        <span class="fa fa-key form-control-feedback"></span>
      </div>
       <div class="form-group has-feedback">
       	<img class="form-control"  id="validateCodeImg" src="${__ROOT__}/public/captcha.jpg" />
        <span class="fa fa-photo form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="text" name="captcha" class="form-control" placeholder="Captcha">
        <span class="fa fa-reply form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox" name="rememberMe" > Remember Me
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <input type="submit" class="btn btn-primary btn-block btn-flat" value="Sign In"/>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <div class="social-auth-links text-center">
      <p>- OR -</p>
      <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign in using
        Facebook</a>
      <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign in using
        Google+</a>
    </div>
    <!-- /.social-auth-links -->

    <a href="#">I forgot my password</a><br>
    <a href="register.html" class="text-center">Register a new membership</a>

  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

  <!-- iCheck -->
  <link rel="stylesheet" href="${__STATIC__}/plugins/iCheck/square/blue.css">
<!-- iCheck -->
<script src="${__STATIC__}/plugins/iCheck/icheck.min.js"></script>
<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
    $("#validateCodeImg").click(function(event){
    	event.preventDefault();
    	$(this).attr("src", "${__ROOT__}/public/captcha.jpg?data=" + new Date() + Math.floor(Math.random() * 24));
    })
    
  });
</script>
<jsp:include page="../debris/Foot.jsp"/>