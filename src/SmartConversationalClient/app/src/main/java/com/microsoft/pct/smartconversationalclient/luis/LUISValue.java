package com.microsoft.pct.smartconversationalclient.luis;

/* Generated by JavaFromJSON */
/*http://javafromjson.dashingrocket.com*/

public class LUISValue {
    @com.fasterxml.jackson.annotation.JsonProperty("type")
    private String _type;

    public void setType(String type) {
        this._type = type;
    }

    public String getType() {
        return _type;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("score")
    private Double _score;

     public void setScore(Double score) {
        this._score = score;
    }

    public Double getScore() {
        return _score;
    }

    @com.fasterxml.jackson.annotation.JsonProperty("entity")
    private String _entity;

    public void setEntity(String entity) {
        this._entity = entity;
    }

    public String getEntity() {
        return _entity;
    }

}