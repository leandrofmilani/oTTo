package br.com.megaroot.milani.otto.Preferences;

/**
 * Created by Josemar on 21/10/2015.
 */
public class Preferences {

    private String descricao;
    private String cod;
    private long id;

    public Preferences(){

    }

    public Preferences(String nome, String email, String senha){
        this.descricao = nome;

    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
