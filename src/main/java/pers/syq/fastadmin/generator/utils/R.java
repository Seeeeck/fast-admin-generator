package pers.syq.fastadmin.generator.utils;

import cn.hutool.http.HttpStatus;
import lombok.Data;
import pers.syq.fastadmin.generator.enums.ErrorCode;

@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    private R() {
    }

    public static R<?> ok() {
        R<?> r = new R<>();
        r.setCode(0);
        r.setMsg("success");
        return r;
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setData(data);
        r.setCode(0);
        r.setMsg("success");
        return r;
    }


    public static R<?> error() {
        R<?> r = new R<>();
        r.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        r.setMsg("unknown error");
        return r;
    }

    public static <T> R<T> error(T data) {
        R<T> r = new R<>();
        r.setData(data);
        r.setCode(HttpStatus.HTTP_INTERNAL_ERROR);
        r.setMsg("unknown error");
        return r;
    }

    public R<?> errorCode(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        return this;
    }

    public R<?> code(int code) {
        this.code = code;
        return this;
    }

    public R<?> msg(String msg) {
        this.msg = msg;
        return this;
    }
}
