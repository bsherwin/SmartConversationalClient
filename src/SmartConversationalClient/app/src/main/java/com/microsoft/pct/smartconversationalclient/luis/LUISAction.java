package com.microsoft.pct.smartconversationalclient.luis;
/* Generated by JavaFromJSON */
/*http://javafromjson.dashingrocket.com*/

public class LUISAction {
    @com.fasterxml.jackson.annotation.JsonProperty("triggered")
    private Boolean _triggered;

    public void setTriggered(Boolean triggered) {
        this._triggered = triggered;
    }

    public Boolean getTriggered() {
        return _triggered;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("parameters")
    private LUISParameter[] _parameters;

    public void setParameters(LUISParameter[] parameters) {
        this._parameters = parameters;
    }

    public LUISParameter[] getParameters() {
        return _parameters;
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