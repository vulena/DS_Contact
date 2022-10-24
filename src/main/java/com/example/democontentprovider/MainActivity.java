package com.example.democontentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvdanhba;
    ArrayList<Contact> dsdanhba;
    TextView tvdanhba;
    ArrayAdapter<Contact> danhBaArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // insertDictionary(new Dictionary("Words Sample","vn"));
       // getAllDictionary();
        tvdanhba = (TextView) findViewById(R.id.tvdanhba);
        if (ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{""+
                    "android.permission.READ_CONTACTS"},1002);
        }
        lvdanhba=(ListView) findViewById(R.id.lvDanhba);
        dsdanhba = new ArrayList<>();
        danhBaArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,dsdanhba);
        lvdanhba.setAdapter(danhBaArrayAdapter);

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        dsdanhba.clear();
        while (cursor.moveToNext()){
            String tencotname= ContactsContract.Contacts.DISPLAY_NAME;
            String tencotphone= ContactsContract.CommonDataKinds.Phone.NUMBER;
            int vitricotname = cursor.getColumnIndex(tencotname);
            int vitricotphone = cursor.getColumnIndex(tencotphone);
            String name= cursor.getString(vitricotname);
            String phone = cursor.getString(vitricotphone);
            Contact contact= new Contact(phone,name);
            dsdanhba.add(contact);
            danhBaArrayAdapter.notifyDataSetChanged();

        }
        lvdanhba.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,ChiTietContacts.class);
                intent.putExtra("Dulieu",dsdanhba.get(i).toString());
                startActivity(intent);
            }
        });
    }
    public void insertDictionary(Dictionary dictionary){
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserDictionary.Words.LOCALE,dictionary.getLocale());
        contentValues.put(UserDictionary.Words.WORD,dictionary.getWords());

        getContentResolver().insert(UserDictionary.Words.CONTENT_URI,contentValues);

    }
    private List<Dictionary> getAllDictionary(){
        ArrayList<Dictionary> dictionaries = new ArrayList<>();
        String[] projection = {UserDictionary.Words.LOCALE, UserDictionary.Words.WORD};
        Cursor cursor = getContentResolver().query(UserDictionary.Words.CONTENT_URI,projection,null,null,null);
        try {
            if (null!=cursor){
                int idLocale=cursor.getColumnIndex(UserDictionary.Words.LOCALE);
                int idWords=cursor.getColumnIndex(UserDictionary.Words.WORD);
                while (cursor.moveToNext()){
                    String word = cursor.getString(idWords);
                    String locale= cursor.getString(idLocale);
                    Dictionary dictionary= new Dictionary(locale,word);
                    dictionaries.add(dictionary);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
        return dictionaries;
    }
}
class Dictionary{
    private String locale,words;

    public Dictionary(String locale, String words) {
        this.locale = locale;
        this.words = words;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}