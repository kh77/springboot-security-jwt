package com.sm.app.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.app.business.service.UserService;
import com.sm.app.shared.dto.UserDto;
import com.sm.app.web.request.UserDetailsRequestModel;
import com.sm.app.web.response.UserRest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users") // http://localhost:8080/users
// @CrossOrigin(origins= {"http://localhost:8083", "http://localhost:8084"})
public class UserController {

	@Autowired
	UserService userService;

	@ApiOperation(value = "The Get User Details Web Service Endpoint", notes = "${userController.GetUser.ApiOperation.Notes}")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header") })
	@GetMapping(path = "/{id}",produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(userDto, UserRest.class);

		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		UserRest returnValue = new UserRest();

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		returnValue = modelMapper.map(createdUser, UserRest.class);

		return returnValue;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header") })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		userDto = new ModelMapper().map(userDetails, UserDto.class);

		UserDto updateUser = userService.updateUser(id, userDto);
		returnValue = new ModelMapper().map(updateUser, UserRest.class);

		return returnValue;
	}
	//
	// @DeleteMapping(path = "/{id}", produces = {
	// MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	// @ApiImplicitParams({
	// @ApiImplicitParam(name="authorization",
	// value="${userController.authorizationHeader.description}",
	// paramType="header")
	// })
	// public OperationStatusModel deleteUser(@PathVariable String id) {
	// OperationStatusModel returnValue = new OperationStatusModel();
	// returnValue.setOperationName(RequestOperationName.DELETE.name());
	//
	// userService.deleteUser(id);
	//
	// returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
	// return returnValue;
	// }
	//
	// @ApiImplicitParams({
	// @ApiImplicitParam(name="authorization",
	// value="${userController.authorizationHeader.description}",
	// paramType="header")
	// })
	// @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE,
	// MediaType.APPLICATION_JSON_VALUE })
	// public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue
	// = "0") int page,
	// @RequestParam(value = "limit", defaultValue = "2") int limit) {
	// List<UserRest> returnValue = new ArrayList<>();
	//
	// List<UserDto> users = userService.getUsers(page, limit);
	//
	// Type listType = new TypeToken<List<UserRest>>() {
	// }.getType();
	// returnValue = new ModelMapper().map(users, listType);
	//
	// /*for (UserDto userDto : users) {
	// UserRest userModel = new UserRest();
	// BeanUtils.copyProperties(userDto, userModel);
	// returnValue.add(userModel);
	// }*/
	//
	// return returnValue;
	// }

	// /*
	// *
	// http://localhost:8080/app-ws/users/email-verification?token=sdfsdf
	// * */
	// @GetMapping(path = "/email-verification", produces = {
	// MediaType.APPLICATION_JSON_VALUE,
	// MediaType.APPLICATION_XML_VALUE })
	// public OperationStatusModel verifyEmailToken(@RequestParam(value =
	// "token") String token) {
	//
	// OperationStatusModel returnValue = new OperationStatusModel();
	// returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
	//
	// boolean isVerified = userService.verifyEmailToken(token);
	//
	// if(isVerified)
	// {
	// returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
	// } else {
	// returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
	// }
	//
	// return returnValue;
	// }
	//
	// /*
	// * http://localhost:8080/mobile-app-ws/users/password-reset-request
	// * */
	// @PostMapping(path = "/password-reset-request",
	// produces = {MediaType.APPLICATION_JSON_VALUE,
	// MediaType.APPLICATION_XML_VALUE},
	// consumes = {MediaType.APPLICATION_JSON_VALUE,
	// MediaType.APPLICATION_XML_VALUE}
	// )
	// public OperationStatusModel requestReset(@RequestBody
	// PasswordResetRequestModel passwordResetRequestModel) {
	// OperationStatusModel returnValue = new OperationStatusModel();
	//
	// boolean operationResult =
	// userService.requestPasswordReset(passwordResetRequestModel.getEmail());
	//
	// returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
	// returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
	//
	// if(operationResult)
	// {
	// returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
	// }
	//
	// return returnValue;
	// }
	//
	//
	//
	// @PostMapping(path = "/password-reset",
	// consumes = {MediaType.APPLICATION_JSON_VALUE,
	// MediaType.APPLICATION_XML_VALUE}
	// )
	// public OperationStatusModel resetPassword(@RequestBody PasswordResetModel
	// passwordResetModel) {
	// OperationStatusModel returnValue = new OperationStatusModel();
	//
	// boolean operationResult = userService.resetPassword(
	// passwordResetModel.getToken(),
	// passwordResetModel.getPassword());
	//
	// returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
	// returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
	//
	// if(operationResult)
	// {
	// returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
	// }
	//
	// return returnValue;
	// }

}
