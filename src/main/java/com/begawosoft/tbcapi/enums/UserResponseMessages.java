package com.begawosoft.tbcapi.enums;

public enum UserResponseMessages {

	LOGIN_SUCCESS("Loged in succesfully"),
    LOGIN_PASSWORD("Incorrect password"),
    USER_ALREADY_AVAILABLE("User already Available"),
    USER_NOT_FOUND("User not found"),
    BLOCKED_ACCOUNT("Your account is blocked because of multiple invalid attempts"),
    USER_ADDED("User added successfully!"),
	USER_MOVED_TO_RECYCLEBIN("User Moved to Recycle Bin"),
	USER_RESTORED("User Restored"),
    USER_UPDATED("User updated successfully!"),
    USER_DELETED("User deleted successfully!"),
    OPERATION_FAILED("Operation Failed");
	
	private String authResponsseMessage;

    private UserResponseMessages(String authResponsseMessage) {
        this.authResponsseMessage = authResponsseMessage;
    }

    public String getAuthResponseMessage() {
        return this.authResponsseMessage;
    }

    public void setAuthResponseMessage(String authResponseMessage) {
        this.authResponsseMessage = authResponseMessage;
    }

}
