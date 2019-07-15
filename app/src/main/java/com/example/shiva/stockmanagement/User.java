package com.example.shiva.stockmanagement;

import java.io.Serializable;

class User implements Serializable {
    public int id;
    public String itemname;
    public String qty;

    public User()
    {

    }

    public User(int id,String itemname, String qty) {
        this.id = id;
        this.itemname = itemname;
        this.qty = qty;
    }
    @Override
    public String toString() {
        return "Id: " + id +
                "\nitemname: " + itemname +
                "\nqty: " + qty ;

    }


}
