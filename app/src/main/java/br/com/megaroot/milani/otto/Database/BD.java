package br.com.megaroot.milani.otto.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.megaroot.milani.otto.Preferences.Preferences;
import br.com.megaroot.milani.otto.Classes.Show;

/**
 * Created by Josemar on 21/10/2015.
 */
public class BD {
    private Context context;
    private SQLiteDatabase bd;

    public BD(Context ctx){
        BDCore auxBd = new BDCore(ctx);
        //inicializando o Database com permissao de escrita
        bd = auxBd.getWritableDatabase();
        //auxoBd.getReadableDatabase apenas leitura
    }

    //agora posso fazer meu CRUD
    public void inserirPreferencia(Preferences pref){
        // bd.execSQL("truncate table preferencias");
        //preciso "desmontar o objeto" no ContentValue
        ContentValues dados = new ContentValues();
        //cada put vai o nome do campo da tabela onde ira gravar os dados
        // dados.put("id", pref.getId());

        int verificaGrava = verificaGravada(pref.getCod());

        if(verificaGrava==0){
            dados.put("cod", pref.getCod());
            dados.put("descricao", pref.getDescricao());
            //agora insiro
            bd.insert("preferencias", null, dados);
            Log.d("BANCO", "------ Registros de Preferencia Gravados ---------");
        }else{
            Log.d("BANCO", "------ Registros de Preferencia Atualizados ---------");
        }
    }

    public void deletarPreferencia(String cod){
        int verificaGrava = verificaGravada(cod);

        if(verificaGrava>0) {
            bd.delete("preferencias", "cod =" + cod, null);
            Log.d("BANCO", "------ Registro de Preferencia Deletado  ---------");
        }
    }

    public void atualizarPreferencia(Preferences pref){
        ContentValues dados = new ContentValues();
        dados.put("descricao", pref.getDescricao());
        //rotina de atualização
        /*
        bd.update("usuario",dados,"_id = ?", new String[]{""+usr.getId()});
        */
        //update table usuario set (nome=nome,email=email) where id = X

        //O QUE ACONTECE:
        //atualiza na tabela "usuario" os content Values
        //PODERIA SER:
        bd.update("preferencias", dados, "_id = " + pref.getId(), null);
        //ou ainda, se gosta tanto de sql, faz no banco:
        //bd.execSQL("update table usuario...");

        Log.d("BANCO", "------Atualizou Registro ---------");

    }//fim do update

    public void deletarPreferenciaANTES(Preferences pref){
        bd.delete("preferencias", "_id = " + pref.getId(), null);
        //ou poderia fazer no banco
        //bd.execSQL("delete from usuario where id = "+usr.getId());
        Log.d("BANCO", "------Deletou Registro ---------");
    }

    public Preferences buscarPreferencia (int id){
        Preferences pref = new Preferences();
        String[] colunas = new String[]{"id","descricao"};
        Cursor cursor = bd.query("preferencias", colunas,"_id ="+id,null,null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            pref.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            pref.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
        }
        Log.d("BANCO", "------Consultou preferencia ---------");
        //retorna o usuario buscado
        return pref;
    }


    public int verificaGravada (String cod){
        int flag;
        String[] colunas = new String[]{"cod","descricao"};
        Cursor cursor = bd.query("preferencias", colunas,"cod ="+cod,null,null,null,null);
        flag = cursor.getCount();
        Log.d("BANCO", "------ Verificou preferencia gravada ---------");
        return flag;
    }

    /**
     * DOS SHOWS
     **/

    public void inserirShow(Show show){
        // bd.execSQL("truncate table preferencias");
        //preciso "desmontar o objeto" no ContentValue
        ContentValues dados = new ContentValues();
        //cada put vai o nome do campo da tabela onde ira gravar os dados
        // dados.put("id", pref.getId());

        dados.put("band", show.getBand());
        dados.put("thumbnailUrl", show.getThumbnailUrl());
        dados.put("date", show.getDate());
        dados.put("city", show.getCity());
        dados.put("place", show.getPlace());
        dados.put("country", show.getCountry());
        dados.put("code", show.getCode());
        //agora insiro
        bd.insert("shows", null, dados);
        Log.d("BANCO", "------ Registros de Shows Gravados ---------");

    }

    public List<Show> buscarShowsNacionais(){
        // Criação de uma Lista de shows
        List<Show> listaShows = new ArrayList<Show>();
        String[] colunas = new String[]{"band","thumbnailUrl","place","city","date","country"};
        Cursor cursor = bd.query("shows", colunas,"country = 0",null,null,null,null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                Show show = new Show();
                show.setCountry(cursor.getInt(cursor.getColumnIndex("country")));
                show.setDate(cursor.getString(cursor.getColumnIndex("date")));
                show.setCity(cursor.getString(cursor.getColumnIndex("city")));
                show.setPlace(cursor.getString(cursor.getColumnIndex("place")));
                show.setThumbnailUrl(cursor.getString(cursor.getColumnIndex("thumbnailUrl")));
                show.setBand(cursor.getString(cursor.getColumnIndex("band")));
                listaShows.add(show);
            }while(cursor.moveToNext());
        }
        Log.d("BANCO", "------ buscarShowsNacionais ---------");
        //retorna lista de shows nacionais
        return listaShows;
    }

    public List<Show> buscarShowsInternacionais(){
        // Criação de uma Lista de shows
        List<Show> listaShows = new ArrayList<Show>();
        String[] colunas = new String[]{"band","thumbnailUrl","place","city","date","country"};
        Cursor cursor = bd.query("shows", colunas,"country = 1",null,null,null,null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                Show show = new Show();
                show.setCountry(cursor.getInt(cursor.getColumnIndex("country")));
                show.setDate(cursor.getString(cursor.getColumnIndex("date")));
                show.setCity(cursor.getString(cursor.getColumnIndex("city")));
                show.setPlace(cursor.getString(cursor.getColumnIndex("place")));
                show.setThumbnailUrl(cursor.getString(cursor.getColumnIndex("thumbnailUrl")));
                show.setBand(cursor.getString(cursor.getColumnIndex("band")));
                listaShows.add(show);
            }while(cursor.moveToNext());
        }
        Log.d("BANCO", "------ buscarShowsInternacionais ---------");
        //retorna lista de shows nacionais
        return listaShows;
    }

    public List<Show> buscarTodosShows(){
        // Criação de uma Lista de shows
        List<Show> listaShows = new ArrayList<Show>();
        String[] colunas = new String[]{"band","thumbnailUrl","place","city","date","country"};
        Cursor cursor = bd.query("shows", colunas,null,null,null,null,"_id DESC");

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                Show show = new Show();
                show.setCountry(cursor.getInt(cursor.getColumnIndex("country")));
                show.setDate(cursor.getString(cursor.getColumnIndex("date")));
                show.setCity(cursor.getString(cursor.getColumnIndex("city")));
                show.setPlace(cursor.getString(cursor.getColumnIndex("place")));
                show.setThumbnailUrl(cursor.getString(cursor.getColumnIndex("thumbnailUrl")));
                show.setBand(cursor.getString(cursor.getColumnIndex("band")));
                listaShows.add(show);
            }while(cursor.moveToNext());
        }
        Log.d("BANCO", "------ Consultou todos shows ---------");
        //retorna lista de todos shows
        return listaShows;
    }

    public Boolean verificaShowGravado (String code){
        int cont;
        String[] colunas = new String[]{"code"};
        Cursor cursor = bd.query("shows", colunas,"code ="+code,null,null,null,null);
        cont = cursor.getCount();
        if (cont > 0) {
            Log.d("BANCO", "------ Nao encontrou show novo ---------");
            return true;
        }else {
            Log.d("BANCO", "------ Encontrou show novo ---------");
            return false;
        }
    }


}
