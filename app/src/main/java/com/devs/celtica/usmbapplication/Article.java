package com.devs.celtica.usmbapplication;

/**
 * Created by celtica on 25/06/18.
 */

public class Article extends Publication {

    public int id_article;
    public String url_img;


    public Article(int id, String type, String date_pub, String titre, String descreption, int id_article, String url_img) {
        super(id, type, date_pub, titre, descreption);
        this.id_article = id_article;
        this.url_img = url_img;
    }
}
