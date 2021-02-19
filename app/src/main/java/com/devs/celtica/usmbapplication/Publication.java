package com.devs.celtica.usmbapplication;

/**
 * Created by celtica on 25/06/18.
 */

public class Publication {
    public int id;
    public String type;
    public String date_pub;
    public String titre;
    public String descreption;



    public Publication(int id, String type, String date_pub, String titre, String descreption) {
        this.id = id;
        this.type = type;
        this.date_pub = date_pub;
        this.titre = titre;
        this.descreption = descreption;
    }
}
