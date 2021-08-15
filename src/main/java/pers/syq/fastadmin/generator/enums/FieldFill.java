package pers.syq.fastadmin.generator.enums;

import java.util.HashMap;
import java.util.Map;

public enum FieldFill {
    NONE(0, null),
    DEFAULT(1, "FieldFill.DEFAULT"),
    INSERT(2, "FieldFill.INSERT"),
    UPDATE(3, "FieldFill.UPDATE"),
    INSERT_UPDATE(4, "FieldFill.INSERT_UPDATE");

    private final Integer code;
    private final String msg;
    private static final Map<Integer, FieldFill> codeLookup = new HashMap<>();

    static {
        for (FieldFill fieldFill : FieldFill.values()) {
            codeLookup.put(fieldFill.getCode(), fieldFill);
        }
    }

    FieldFill(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static FieldFill getFieldFill(int code) {
        return codeLookup.get(code);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return this.getMsg();
    }
}
