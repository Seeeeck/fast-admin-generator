package pers.syq.fastadmin.generator.exception;

import cn.hutool.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;
import pers.syq.fastadmin.generator.enums.ErrorCode;

@Getter
@Setter
public class FastAdminException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String msg;
    private int code;

    public FastAdminException(){
        this.code = HttpStatus.HTTP_INTERNAL_ERROR;
        this.msg = "unknown error";
    }
    public FastAdminException(String msg, int code){
        this.msg = msg;
        this.code = code;
    }

    public FastAdminException(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }
}
