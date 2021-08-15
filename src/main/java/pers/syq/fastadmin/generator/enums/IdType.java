package pers.syq.fastadmin.generator.enums;

import java.util.HashMap;
import java.util.Map;

public enum IdType {
    NONE(0, "IdType.NONE"),
    AUTO(1, "IdType.AUTO"),
    INPUT(2, "IdType.INPUT"),
    ASSIGN_ID(3, "IdType.ASSIGN_ID"),
    ASSIGN_UUID(4, "IdType.ASSIGN_UUID");

    private final Integer code;
    private final String msg;
    private static final Map<Integer, IdType> codeLookup = new HashMap<>();

    static {
        for (IdType idType : IdType.values()) {
            codeLookup.put(idType.getCode(), idType);
        }
    }

    IdType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static IdType getIdType(int code) {
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
