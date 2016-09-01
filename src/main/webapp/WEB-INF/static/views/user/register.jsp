<jsp:include page="../debris/Head.jsp"/>
<body class="hold-transition register-page">
<div class="register-box">
  <div class="register-logo">
    <a href="${__STATIC__}/index2.html"><b>Admin</b>LTE</a>
  </div>

  <div class="register-box-body">
    <p class="login-box-msg">Register a new membership</p>

    <form action="${__ROOT__}/user/register" method="post">
      <div class="form-group has-feedback">
        <input type="text" name="username" class="form-control" placeholder="Full name">
        <span class="fa fa-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="email"  name="email" class="form-control" placeholder="Email">
        <span class="fa fa-envelope-o form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="number"  name="phone" class="form-control" placeholder="phone">
        <span class="fa fa-mobile-phone form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="password" class="form-control" placeholder="Password">
        <span class="fa fa-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="respassword"  class="form-control" placeholder="Retype password">
        <span class="fa fa-sign-in form-control-feedback"></span>
      </div>
       <div class="form-group has-feedback">
       	<img class="form-control"  id="validateCodeImg" src="${__ROOT__}/public/captcha.jpg" />
        <span class="fa fa-photo form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="text"  name="captcha" class="form-control" placeholder="Captcha">
        <span class="fa fa-reply form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox"> I agree to the <a href="#">terms</a>
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <input type="submit" class="btn btn-primary btn-block btn-flat" value="Register"/>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <div class="social-auth-links text-center">
      <p>- OR -</p>
      <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign up using
        Facebook</a>
      <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign up using
        Google+</a>
    </div>

    <a href="login.html" class="text-center">I already have a membership</a>
  </div>
  <!-- /.form-box -->
</div>
<!-- /.register-box -->
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
