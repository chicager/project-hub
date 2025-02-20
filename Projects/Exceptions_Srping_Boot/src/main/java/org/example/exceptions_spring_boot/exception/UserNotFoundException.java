package org.example.exceptions_spring_boot.exception;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(Long id) {
        super("user.not.found", id);
    }
}
