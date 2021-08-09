package pers.syq.fastadmin.generator.enums;

public enum FieldFill {
    NONE(0,null),
    DEFAULT(1,"FieldFill.DEFAULT"),
    INSERT(2,"FieldFill.INSERT"),
    UPDATE(3,"FieldFill.UPDATE"),
    INSERT_UPDATE(4,"FieldFill.INSERT_UPDATE");


    private Integer code;
    private String msg;

    FieldFill(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static FieldFill getFieldFill(int code){
        for (FieldFill fieldFill : FieldFill.values()) {
            if (fieldFill.getCode() == code){
                return fieldFill;
            }
        }
        return NONE;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString(){
        return this.getMsg();
    }
}
