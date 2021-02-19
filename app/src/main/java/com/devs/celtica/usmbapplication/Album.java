package com.devs.celtica.usmbapplication;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by celtica on 25/06/18.
 */

public class Album extends Publication {
    public int id_album;
    public ArrayList<PhotoAlbum> MyImg=new ArrayList<PhotoAlbum>();


    public Album(int id, String type, String date_pub, String titre, String descreption, int id_album) {
        super(id, type, date_pub, titre, descreption);
        this.id_album = id_album;

    }

    public  void getImages(MySqlBdServer.doBeforAndAfterGettingData doThis){
        //récupéré les image de ce album ..
        Accueil.BD.read("select url_image from photoalbum where id_album='" + id_album + "' order by id_image asc limit 3", new MySqlBdServer.doBeforAndAfterGettingData() {
            @Override
            public void before() {

            }

            @Override
            public void After()   {

                try {
                    JSONArray r=new JSONArray(Accueil.BD.queryResult);
                    int i=0;

                    while(i<r.length()){
                        MyImg.add(new PhotoAlbum(r.getJSONObject(i).getString("url_image"),id_album));
                        i++;

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
