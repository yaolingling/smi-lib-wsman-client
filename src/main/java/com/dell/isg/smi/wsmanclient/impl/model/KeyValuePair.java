package com.dell.isg.smi.wsmanclient.impl.model;

/**
 * @author Umer_Shabbir
 */

public class KeyValuePair {

    private String Key;
    private String value;

    public KeyValuePair() {
    }

    public KeyValuePair(String k, String v) {
        Key = k;
        value = v;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
