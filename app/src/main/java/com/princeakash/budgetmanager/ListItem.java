package com.princeakash.budgetmanager;

public class ListItem {

    private String category;
    private String amount;
    private String id;
    private String date;

    public ListItem(String id, String amount, String category, String date) {
        this.category = category;
        this.amount = amount;
        this.id = id;
        this.date = date;
    }

    public String getCategory(){
        return category;
    }

    public String getAmount(){
        return amount;
    }

    public String getId(){
        return id;
    }

    public String getDate(){
        return date;
    }

}

