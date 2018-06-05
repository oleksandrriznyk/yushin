<%@include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@include file="/WEB-INF/jspf/header.jspf"%>
<!-- content-wrapper-->
<div id="content-wrapper" class="container">
	<!-- content-->
	<div align="center">REGISTRATION</div>
	<form class="form-horizontal" action='controller' method="POST">
		<fieldset>
			<input type="hidden" name="command" value="createUser" />
			<div class="control-group">
				<!-- Login -->
				<label>Login</label>
				<div class="controls">
					<input type="text" id="login" name="login" placeholder="">
				</div>
			</div>

			<div>
				<!-- E-mail -->
				<label class="control-label" for="email">E-mail</label>
				<div class="controls">
					<input type="text" id="email" name="email" placeholder="">
				</div>
			</div>

			<div>
				<!-- Password-->
				<label class="control-label" for="password">Password</label>
				<div class="controls">
					<input type="password" id="password" name="password" placeholder="">
				</div>
			</div>

			<div>
				<!-- Password -->
				<label for="password_confirm">Password (Confirm)</label>
				<div class="controls">
					<input type="password" id="password_confirm" name="passwordConfirm"
						placeholder="">
				</div>
			</div>

			<div>
				<!-- First name -->
				<label for="password_confirm">First name</label>
				<div class="controls">
					<input type="text" id="first_name" name="firstName" placeholder="">
				</div>
			</div>

			<div>
				<!--Last name -->
				<label for="password_confirm">Last name</label>
				<div class="controls">
					<input type="text" id="last_name" name="lastName" placeholder="">
				</div>
			</div>

			<div>
				<!--Gender -->
				<label for="password_confirm">Gender</label> 
				<input type="radio" name="gender" value="male"> Male
				<input type="radio" name="gender" value="female">Female<br>

			</div>
			<div>
				<!-- Button -->
				<div>
					<button class="btn btn-success">Register</button>
				</div>
			</div>
		</fieldset>
	</form>

	<!-- /content-->
</div>
<!-- /content-wrapper-->
<%@include file="/WEB-INF/jspf/footer.jspf"%>