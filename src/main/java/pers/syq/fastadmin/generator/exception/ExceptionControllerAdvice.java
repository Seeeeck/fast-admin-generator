package pers.syq.fastadmin.generator.exception;


import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pers.syq.fastadmin.generator.utils.R;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestControllerAdvice(basePackages = "pers.syq.fastadmin.generator")
public class ExceptionControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String,Object> map = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return R.error(map).code(HttpStatus.HTTP_BAD_REQUEST).msg("bad request");
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public R<?> handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        HashMap<String,Object> map = new HashMap<>();
        for (ConstraintViolation<?> violation : constraintViolations) {
            String[] strings = violation.getPropertyPath().toString().split("\\.");
            map.put(strings[1],violation.getMessage());
        }
        return R.error(map).code(HttpStatus.HTTP_BAD_REQUEST).msg("bad request");
    }

    @ExceptionHandler({BindException.class})
    public R<?> handleBindExceptionException(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String,Object> map = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return R.error(map).code(HttpStatus.HTTP_BAD_REQUEST).msg("bad request");
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public R<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error(String.valueOf(ex.getCause()));
        HashMap<String, Object> map = new HashMap<>();
        map.put(ex.getName(),"null");
        return R.error(map).code(HttpStatus.HTTP_BAD_REQUEST).msg("bad request");
    }


    @ExceptionHandler({MissingServletRequestParameterException.class})
    public R<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return R.error().code(HttpStatus.HTTP_BAD_REQUEST).msg("bad request");
    }

    @ExceptionHandler({FastAdminException.class})
    public R<?> handleFastAdminException(FastAdminException e){
        return R.error().code(e.getCode()).msg(e.getMsg());
    }

    @ExceptionHandler({Exception.class})
    public R<?> handleAllException(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error();
    }

}
