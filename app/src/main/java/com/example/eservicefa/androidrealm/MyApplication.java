package com.example.eservicefa.androidrealm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jimmy on 09/11/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        // Permet de récupérer une instance de Realm pour ce thread
        // Avec une configuration par defaut
        // Realm realm = Realm.getDefaultInstance()

        // Cette configuration sans options utilise le fichier default.realm qui se trouve dans Context.getFilesDir()
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

    }
}
