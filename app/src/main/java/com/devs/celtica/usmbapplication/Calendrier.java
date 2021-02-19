package com.devs.celtica.usmbapplication;

import java.util.ArrayList;

/**
 * Created by celtica on 04/07/18.
 */

public class Calendrier {
    public String saison;
    public  ArrayList<Match> Matches;

    public Calendrier(String saison) {
        this.saison = saison;
        Matches=new ArrayList<Match>();
    }
}
