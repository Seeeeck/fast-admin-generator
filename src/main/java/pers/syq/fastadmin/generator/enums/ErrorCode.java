package pers.syq.fastadmin.generator.enums;

public enum ErrorCode {
    SQL_EXCEPTION(1000,"Bad datasource"),
    NULL_DATABASE(1001,"Null datasource"),
    NULL_GLOBAL_CONFIG(1100,"Null global configuration");

    private Integer code;
    private String msg;
    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
