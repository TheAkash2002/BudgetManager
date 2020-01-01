package com.princeakash.budgetmanager;

public class ListItem {

    private String category;
    private Integer amount;
    private Integer id;
    private String date;

    public ListItem(String category, Integer amount, Integer id, String date) {
        this.category = category;
        this.amount = amount;
        this.id = id;
        this.date = date;
    }

    public String getCategory(){
        return category;
    }

    public Integer getAmount(){
        return amount;
    }

    public Integer getId(){
        return id;
    }

    public String getDate(){
        return date;
    }
}

