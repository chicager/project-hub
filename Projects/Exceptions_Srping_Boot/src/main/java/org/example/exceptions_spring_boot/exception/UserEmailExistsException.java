package org.example.exceptions_spring_boot.exception;

public class UserEmailExistsException extends BaseException {

    public UserEmailExistsException(String email) {
        super("user.email.exists", email);
    }
}
