package com.microsoft.pct.smartconversationalclient.luis;

/* Generated by JavaFromJSON */
/*http://javafromjson.dashingrocket.com*/

public class LUISParameter {
    @com.fasterxml.jackson.annotation.JsonProperty("value")
    private LUISValue[] _value;

    public void setValue(LUISValue[] value) {
        this._value = value;
    }

    public LUISValue[] getValue() {
        return _value;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("required")
    private Boolean _required;

    public void setRequired(Boolean required) {
        this._required = required;
    }

    public Boolean getRequired() {
        return _required;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("name")
    private String _name;

     public void setName(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

}