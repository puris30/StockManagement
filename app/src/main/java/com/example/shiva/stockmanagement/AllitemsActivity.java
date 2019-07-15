package com.example.shiva.stockmanagement;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllitemsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listView)
    ListView listView;

    ArrayAdapter<String> adapter;

    ArrayList<User> users;
    ArrayList<String> users1;

    ContentResolver resolver;



    int pos;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allitems);

        ButterKnife.bind(this);

        users = new ArrayList<>();
        users1 = new ArrayList<>();

        resolver = getContentResolver();

        queryUsersFromDB();



    }

    void queryUsersFromDB(){

        String[] projection = {Util.COL_ID,Util.COL_ITEMNAME,Util.COL_QTY,};
        Cursor cursor = resolver.query(Util.USER_URI,projection,null,null,"Util.COL_ITEMNAME ASC");

        if(cursor != null){

            while (cursor.moveToNext()){

                User user = new User();
                user.id = cursor.getInt(cursor.getColumnIndex(Util.COL_ID));
                user.itemname = cursor.getString(cursor.getColumnIndex(Util.COL_ITEMNAME));
                user.qty = cursor.getString(cursor.getColumnIndex(Util.COL_QTY));


                users.add(user);
                users1.add(user.toString());


            }

            adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,users1);

            //usersAdapter = new UsersAdapter(this,R.layout.list_item,users);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }

    }

    void deleteUserFromDB(){

        String where = Util.COL_ID+" = "+user.id;
        int i = resolver.delete(Util.USER_URI,where,null);

        if(i>0){
            Toast.makeText(this,user.itemname+" Deleted: "+i,Toast.LENGTH_LONG).show();

            users.remove(pos);
            users1.remove(pos);

            // Refresh the ListView
            adapter.notifyDataSetChanged();


        }else{
            Toast.makeText(this,user.itemname+" Not Deleted",Toast.LENGTH_LONG).show();
        }
    }

    void askForDeletion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+user.itemname);
        builder.setMessage("Are You Sure ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteUserFromDB();
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.create().show();
    }

    void showDetails(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details of "+user.itemname);
        builder.setMessage(user.toString());
        builder.setPositiveButton("Done",null);
        builder.create().show();
    }

    void showOptions(){
        String[] items = {"View","Delete","Update","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        showDetails();
                        break;

                    case 1:
                        askForDeletion();
                        break;

                    case 2:
                        Intent intent = new Intent(AllitemsActivity.this,MainActivity.class);
                        intent.putExtra("keyUser",user);
                        startActivity(intent);
                        break;

                    case 3:
                        finish();
                        break;
                }
            }
        });
        builder.create().show();
    }





    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        user = users.get(position);
        showOptions();
    }

}
