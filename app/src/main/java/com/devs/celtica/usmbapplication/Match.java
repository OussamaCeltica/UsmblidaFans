package com.devs.celtica.usmbapplication;

/**
 * Created by celtica on 04/07/18.
 */

public class Match {
    int id_match;
      String local;
  String visiteur;

  String saison;

    public Match(int id_match, String local, String visiteur, String saison) {
        this.id_match = id_match;
        this.local = local;
        this.visiteur = visiteur;

        this.saison = saison;
    }
}
