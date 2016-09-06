	<div class="container w-xxl w-auto-xs" ng-controller="SigninFormController" ng-init="app.settings.container = false;">
	  <a href class="navbar-brand block m-t">{{app.name}}</a>
	  <div class="m-b-lg">
	    <div class="wrapper text-center">
	      <strong>Sign in to get in touch</strong>
	    </div>
	    <form name="form" class="form-validation">
	      <div class="text-danger wrapper text-center" ng-show="authError">
	          {{authError}}
	      </div>
	      <div class="list-group list-group-sm">
	        <div class="list-group-item">
	          <input type="email" placeholder="Email" class="form-control no-border" ng-model="user.email" required>
	        </div>
	        <div class="list-group-item">
	           <input type="password" placeholder="Password" class="form-control no-border" ng-model="user.password" required>
	        </div>
	      </div>
	      <button type="submit" class="btn btn-lg btn-primary btn-block" ng-click="login()" ng-disabled='form.$invalid'>Log in</button>
	      <div class="text-center m-t m-b"><a ui-sref="access.forgotpwd">Forgot password?</a></div>
	      <div class="line line-dashed"></div>
	      <p class="text-center"><small>Do not have an account?</small></p>
	      <a ui-sref="access.signup" class="btn btn-lg btn-default btn-block">Create an account</a>
	    </form>
	  </div>
	  <div class="text-center" ng-include="'tpl/blocks/page_footer.html'"></div>
	</div>
	