package com.example.auth_git.config;

import com.example.auth_git.exception.NotFoundException;
import com.example.auth_git.exception.RecordExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(RecordExistsException.class)
    public ProblemDetail handleRecordExistsException(RecordExistsException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleRuntimeException(RuntimeException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleAllUncaughtException(Exception ex){
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        String detail = "An unexpected error occurred";
        if(ex instanceof MethodArgumentNotValidException e){
            e.getBindingResult().getFieldErrors().forEach(fieldError -> {
                System.out.println(fieldError.getField() + ": " + fieldError.getField() + " " + fieldError.getDefaultMessage());
            });

            status = HttpStatus.BAD_REQUEST;
            detail = "Validation failed";
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);

            var errors = e.getBindingResult().getFieldErrors().stream()
                    .collect(Collectors.toMap(fieldError -> fieldError.getField(), fieldError -> fieldError.getDefaultMessage()));

            problemDetail.setProperty("invalid_params", errors);

            return problemDetail;
        }

        if(ex instanceof ErrorResponse errorResponse){
            status = errorResponse.getStatusCode();
            detail = ex.getMessage();
        } else {
            ResponseStatus rs = ex.getClass().getAnnotation(ResponseStatus.class);
            if(rs != null){
                status = rs.code();
                detail = ex.getMessage();
            }
        }
        return ProblemDetail.forStatusAndDetail(status, detail);
    }
}
