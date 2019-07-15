package com.example.shiva.stockmanagement;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{



    @BindView(R.id.editText)
    EditText eTxtItem;

    @BindView(R.id.editText2)
    EditText eTxtQty;

    @BindView(R.id.button)
    Button btnRegister;

    User user;

    ContentResolver resolver;

    boolean updateMode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyUser");

        btnRegister.setOnClickListener(this);
        user = new User();

        resolver = getContentResolver();

        if(updateMode){
            user = (User)rcv.getSerializableExtra("keyUser");
            eTxtItem.setText(user.itemname);

            eTxtQty.setText(user.qty);
            btnRegister.setText("Update "+user.itemname);
            getSupportActionBar().setTitle("Update Item");

        }

    }


    void insertUserInDB(){

        ContentValues values = new ContentValues();

        values.put(Util.COL_ITEMNAME,user.itemname);
        values.put(Util.COL_QTY,user.qty);


        if(!updateMode) {
            Uri uri = resolver.insert(Util.USER_URI, values);
            Toast.makeText(this, user.itemname + " Registered: " + uri.getLastPathSegment(), Toast.LENGTH_LONG).show();
            clearFields();
        }else{
            String where = Util.COL_ID+" = "+user.id;
            int i = resolver.update(Util.USER_URI,values,where,null);
            if(i>0){
                Toast.makeText(this,user.itemname+" Updated.Updated details will be shown next time you open the app !!"+i,Toast.LENGTH_LONG).show();
                finish();
            }else{
                Toast.makeText(this,user.itemname+" Not Updated !!"+i,Toast.LENGTH_LONG).show();
            }
        }
    }

    void clearFields(){
        eTxtItem.setText("");
        eTxtQty.setText("");
    }

    boolean validateFields(){

        boolean flag = true;

        if(user.itemname.isEmpty())
            flag = false;


        if(user.qty.isEmpty()) {
            flag = false;
        }



        return flag;

    }

    @Override
    public void onClick(View v) {
        user.itemname = eTxtItem.getText().toString().trim();
        user.qty = eTxtQty.getText().toString().trim();


        if(validateFields()) {
            insertUserInDB();
        }else{
            Toast.makeText(this,"Please Enter Correct Details First !!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!updateMode) {
            menu.add(1, 101, 1, "All Items");
        }
        return super.onCreateOptionsMenu(menu);}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == 101){
            Intent intent = new Intent(MainActivity.this,AllitemsActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }
}
