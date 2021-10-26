package com.mda.enums;

public enum ResultEnum {
    /**
     * OK
     */
    OK("OK", "OK"),

    /**
     * NG
     */
    NG("NG", "NG");

    public String key;
    public String value;

    ResultEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ResultEnum parse(String code) {
        if (code == null) {
            return null;
        }
        for (ResultEnum resultEnum : ResultEnum.values()) {
            if (resultEnum.getKey().equals(code)) {
                return resultEnum;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
