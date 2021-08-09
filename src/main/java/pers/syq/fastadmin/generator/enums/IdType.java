package pers.syq.fastadmin.generator.enums;

public enum IdType {
    NONE(0,"IdType.NONE"),
    AUTO(1,"IdType.AUTO"),
    INPUT(2,"IdType.INPUT"),
    ASSIGN_ID(3,"IdType.ASSIGN_ID"),
    ASSIGN_UUID(4,"IdType.ASSIGN_UUID");

    private Integer code;
    private String msg;

    IdType(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static IdType getIdType(int code){
        for (IdType idType : IdType.values()) {
            if (idType.getCode() == code){
                return idType;
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
