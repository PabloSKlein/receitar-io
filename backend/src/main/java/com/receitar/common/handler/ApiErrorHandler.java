package com.receitar.common.handler;

import com.receitar.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ApiErrorHandler {
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorDto handleNotFoundException(NotFoundException exception) {
        return new ApiErrorDto(exception.getMessage());
    }
}
