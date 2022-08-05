package com.sk.op.application.common.controller;

import com.sk.op.application.common.dto.ResponseDto;
import com.sk.op.application.common.dto.ResponseStatus;
import com.sk.op.application.common.exception.RespondException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Hidden
@RestController
@ControllerAdvice
public class ExceptionController extends AbstractErrorController {

    public ExceptionController() {
        super(new DefaultErrorAttributes());
    }

    @ExceptionHandler(Exception.class)
    public ResponseDto<?> handlerException(Exception exception) {
        RespondException responseException = RespondException.extractRespondException(exception);
        if (responseException != null) {
            log.warn("Had a response exception, status: {}, message: {}", responseException.getStatus(), exception.getMessage());
            return responseException.response();
        } else {
            log.error("服务器内部异常", exception);
            return ResponseDto.fail(ResponseStatus.UNKNOWN);
        }
    }

    @ResponseBody
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseDto<?> handlerException(HttpRequestMethodNotSupportedException exception) {
        String message = String.format("不支持的方法: %s", exception.getMethod());
        String[] supportedMethods = exception.getSupportedMethods();
        if (supportedMethods != null && supportedMethods.length > 0) {
            if (supportedMethods.length == 1) {
                message += String.format(", 需要: %s", supportedMethods[0]);
            } else {
                message += String.format(", 需要: %s", Arrays.stream(supportedMethods).collect(Collectors.joining(", ", "[", "]")));
            }
        }
        log.warn("Had a method not support exception, message: {}", message);
        return ResponseDto.fail(ResponseStatus.METHOD_ERROR, message);
    }

    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseDto<?> handlerException(MissingServletRequestParameterException exception) {
        String message = String.format("缺少参数: %s, 类型: %s", exception.getParameterName(), exception.getParameterType());
        log.warn("Had a request parameter exception, message: {}", message);
        return ResponseDto.fail(ResponseStatus.PARAMETER_ERROR, message);
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseDto<?> handlerException(MethodArgumentTypeMismatchException exception) {
        String message = String.format("参数: %s, 类型错误", exception.getName());
        Class<?> requiredType = exception.getRequiredType();
        if (requiredType != null) {
            message += String.format(", 需要: %s", requiredType.getSimpleName());
        }
        log.warn("Had a argument type mismatch exception, message: {}", message);
        return ResponseDto.fail(ResponseStatus.PARAMETER_ERROR, message);
    }

    @ResponseBody
    @ExceptionHandler({BindException.class})
    public ResponseDto<?> handlerException(BindException exception) {
        List<String> messages = new ArrayList<>();
        for (FieldError fieldError : exception.getFieldErrors()) {
            messages.add(String.format("参数: %s, %s", fieldError.getField(), fieldError.getDefaultMessage()));
        }
        String message = String.join(", ", messages);
        log.warn("Had a bind exception, message: {}", message);
        return ResponseDto.fail(ResponseStatus.PARAMETER_ERROR, message);
    }

    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseDto<?> handlerException(ConstraintViolationException exception) {
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            Path.Node paramNode = null;
            for (Path.Node node : violation.getPropertyPath()) {
                if (node.getKind() == ElementKind.PARAMETER) {
                    paramNode = node;
                    break;
                }
            }
            if (paramNode != null) {
                messages.add(String.format("参数: %s, %s", paramNode.getName(), violation.getMessage()));
            } else {
                messages.add(violation.getMessage());
            }
        }
        String message = String.join(", ", messages);
        log.warn("Had a argument type mismatch exception, message: {}", message);
        return ResponseDto.fail(ResponseStatus.PARAMETER_ERROR, message);
    }

    @RequestMapping("${server.error.path:${error.path:/error}}")
    public ResponseDto<?> error(HttpServletRequest request) {
        HttpStatus httpStatus = getStatus(request);
        return ResponseDto.fail(httpStatus.value(), httpStatus.getReasonPhrase());
    }
}
