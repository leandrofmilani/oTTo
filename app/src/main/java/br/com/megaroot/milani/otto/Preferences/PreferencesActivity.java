package br.com.megaroot.milani.otto.Preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import br.com.megaroot.milani.otto.Database.BD;
import br.com.megaroot.milani.otto.R;

public class PreferencesActivity extends AppCompatActivity {

    protected CheckBox rock;
    protected CheckBox mpb;
    protected CheckBox samba;
    protected CheckBox forro;
    protected CheckBox sertanejo;
    protected CheckBox eletronica;
    protected CheckBox axe;
    protected CheckBox funk;
    protected CheckBox country;
    protected CheckBox gospel;
    protected RadioButton notifDiaria;
    protected RadioButton notifSemanal;
    protected RadioButton notifMensal;
    protected Button btnSalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //depois, atrelar cada objeto com seu respectivo widget;
        rock = (CheckBox) findViewById(R.id.checkBoxRock);
        mpb = (CheckBox) findViewById(R.id.checkBoxMPB);
        samba = (CheckBox) findViewById(R.id.checkBoxSamba);
        forro = (CheckBox) findViewById(R.id.checkBoxForro);
        sertanejo = (CheckBox) findViewById(R.id.checkBoxSertanejo);
        eletronica = (CheckBox) findViewById(R.id.checkBoxEletronica);
        axe = (CheckBox) findViewById(R.id.checkBoxAxe);
        funk = (CheckBox) findViewById(R.id.checkBoxFunk);
        country = (CheckBox) findViewById(R.id.checkBoxCountry);
        gospel = (CheckBox) findViewById(R.id.checkBoxGospel);
        notifDiaria = (RadioButton) findViewById(R.id.radioButtonDiaria);
        notifSemanal = (RadioButton) findViewById(R.id.radioButtonSemanal);
        notifMensal = (RadioButton) findViewById(R.id.radioButtonMensal);
        btnSalvar = (Button) findViewById(R.id.buttonTelaPrefSalvar);


        //consulto atuais preferencias salvas no dispositivo
        BD bd = new BD(getApplicationContext());
        int verifRock = bd.verificaGravada("1");
        int verifMpb = bd.verificaGravada("2");
        int verifSamba = bd.verificaGravada("3");
        int verifForro = bd.verificaGravada("4");
        int verifSertanejo = bd.verificaGravada("5");
        int verifEletronica = bd.verificaGravada("6");
        int verifAxe = bd.verificaGravada("7");
        int verifFunk = bd.verificaGravada("8");
        int verifCountry = bd.verificaGravada("9");
        int verifGospel = bd.verificaGravada("10");
        int verifNotifDiaria = bd.verificaGravada("11");
        int verifNotifSemanal = bd.verificaGravada("12");
        int verifNotifMensal = bd.verificaGravada("13");

        //se for maior que 0 esta no banco, entao marca a checkBox
        if(verifRock>0){
            rock.setChecked(true);
        }
        if(verifMpb>0){
            mpb.setChecked(true);
        }
        if(verifSamba>0){
            samba.setChecked(true);
        }
        if(verifForro>0){
            forro.setChecked(true);
        }
        if(verifSertanejo>0){
            sertanejo.setChecked(true);
        }
        if(verifEletronica>0){
            eletronica.setChecked(true);
        }
        if(verifAxe>0){
            axe.setChecked(true);
        }
        if(verifFunk>0){
            funk.setChecked(true);
        }
        if(verifCountry>0){
            country.setChecked(true);
        }
        if(verifGospel>0){
            gospel.setChecked(true);
        }
        if(verifNotifDiaria>0){
            notifDiaria.setChecked(true);
            notifSemanal.setChecked(false);
            notifMensal.setChecked(false);
        }
        if(verifNotifSemanal>0){
            notifSemanal.setChecked(true);
            notifDiaria.setChecked(false);
            notifMensal.setChecked(false);
        }
        if(verifNotifMensal>0){
            notifMensal.setChecked(true);
            notifDiaria.setChecked(false);
            notifSemanal.setChecked(false);
        }




        notifDiaria.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifSemanal.setChecked(false);
                notifMensal.setChecked(false);
            }
        }));

        notifSemanal.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifDiaria.setChecked(false);
                notifMensal.setChecked(false);
            }
        }));

        notifMensal.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifDiaria.setChecked(false);
                notifSemanal.setChecked(false);
            }
        }));

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rock.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("1");
                    pref.setDescricao("rock");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("1");
                }
                if(mpb.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("2");
                    pref.setDescricao("mpb");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("2");
                }
                if(samba.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("3");
                    pref.setDescricao("samba");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("3");
                }
                if(forro.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("4");
                    pref.setDescricao("forro");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("4");
                }
                if(sertanejo.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("5");
                    pref.setDescricao("sertanejo");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("5");
                }
                if(eletronica.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("6");
                    pref.setDescricao("eletronica");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("6");
                }
                if(axe.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("7");
                    pref.setDescricao("axe");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("7");
                }
                if(funk.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("8");
                    pref.setDescricao("funk");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("8");
                }
                if(country.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("9");
                    pref.setDescricao("country");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("9");
                }
                if(gospel.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("10");
                    pref.setDescricao("gospel");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("10");
                }
                if(notifDiaria.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("11");
                    pref.setDescricao("notifDaria");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("11");
                }
                if(notifSemanal.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("12");
                    pref.setDescricao("notifSemanal");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("12");
                }
                if(notifMensal.isChecked()) {
                    Preferences pref = new Preferences();
                    pref.setCod("13");
                    pref.setDescricao("notifMensal");
                    BD bd = new BD(getApplicationContext());
                    bd.inserirPreferencia(pref);
                }else{
                    BD bd = new BD(getApplicationContext());
                    bd.deletarPreferencia("13");
                }

                //Apos salvar fecha a activity
                Toast.makeText(getApplicationContext(), "Preferências salvas com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }



}
