package com.squadio.exception.handler;

import com.squadio.exception.BadRequestException;
import com.squadio.exception.DuplicateLoginException;
import com.squadio.exception.NotFoundException;
import com.squadio.exception.UnAuthorizedException;
import com.squadio.responseDto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(Exception exception) {

        log.info("error message: {}", exception.getMessage());
        ApiResponse<String> apiResponse = new ApiResponse<>(exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(Exception exception) {

        log.info("error message: {}", exception.getMessage());
        ApiResponse<String> apiResponse = new ApiResponse<>(exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UnAuthorizedException.class})
    public ResponseEntity<ApiResponse<String>> handleUnauthorised(Exception exception) {

        log.info("error message: {}", exception.getMessage());
        ApiResponse<String> apiResponse = new ApiResponse<>(exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {DuplicateLoginException.class})
    public ResponseEntity<ApiResponse<String>> processDuplicateLoginException(Exception exception) {

        log.info("error message: {}", exception.getMessage());
        ApiResponse<String> apiResponse = new ApiResponse<>(exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

}
