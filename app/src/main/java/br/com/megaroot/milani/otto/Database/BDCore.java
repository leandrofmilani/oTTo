package br.com.megaroot.milani.otto.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Josemar on 21/10/2015.
 */
public class BDCore extends SQLiteOpenHelper{

    public static final String NOME_BD = "dboTTo";
    public static final int VERSAO_BD = 10;
    public static String QUERY_CREATE_USUARIO = "create table preferencias(_id integer primary key autoincrement, cod text not null, descricao text not null);";
    public static String DROP_TABLE = "drop table preferencias;";
    public static String QUERY_CREATE_SHOWS = "create table shows(_id integer primary key autoincrement, code text not null, band text, thumbnailUrl text, date text, city text, place text, country integer);";
    public static String DROP_SHOWS = "drop table shows;";

    //contrutor da classe
    public BDCore(Context ctx){
        //aqui eu de fato inicializo o banco
        super(ctx,NOME_BD,null,VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //vamos criar a tabela de usuarios
        db.execSQL(QUERY_CREATE_USUARIO);
        db.execSQL(QUERY_CREATE_SHOWS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //quando atualizar o banco vai fazer isso
        //antes de apagar o banco serria conveniente salvar os dados
        //1- apaga o banco atual
        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_SHOWS);
        //2- crio novamente
        onCreate(db);
        //poderia popular a tabela com os dados

    }


}
