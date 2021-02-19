package com.devs.celtica.usmbapplication;

/**
 * Created by celtica on 25/06/18.
 */

public class Video extends Publication {
    public int id_video;
    public String url_video;


    public Video(int id, String type, String date_pub, String titre, String descreption, int id_video, String url_video) {
        super(id, type, date_pub, titre, descreption);
        this.id_video = id_video;
        this.url_video = url_video;
    }
}
