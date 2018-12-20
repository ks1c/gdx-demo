//SINGLETON PARA MOSTRAR ERROS

package com.ks1c.gdxdemo;

public class DMsg {

    private static DMsg instance;

    private String textIn;

    private DMsg(String textIn) {

        this.textIn = textIn;
    }

    public static synchronized String show(String textIn) {

        if (instance == null) {
            instance = new DMsg(textIn);
            return instance.textIn;
        } else {
            instance.textIn = textIn;
            return instance.textIn;
        }
    }

    public static synchronized String show() {

        if (instance == null) {
            instance = new DMsg("");
            return instance.textIn;
        } else {
            return instance.textIn;
        }
    }
}
