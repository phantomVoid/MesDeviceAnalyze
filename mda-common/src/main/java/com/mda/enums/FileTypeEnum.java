package com.mda.enums;

public enum FileTypeEnum {
    /**
     * SPI
     */
    SPI("SPI", "SPI"),

    /**
     * HLH
     */
    HLH("HLH", "HLH"),

    /**
     * AOI
     */
    AOI("AOI", "AOI");

    public String key;
    public String value;

    FileTypeEnum(String key, String value) {
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

    public static FileTypeEnum parse(String code) {
        if (code == null) {
            return null;
        }
        for (FileTypeEnum resultEnum : FileTypeEnum.values()) {
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
