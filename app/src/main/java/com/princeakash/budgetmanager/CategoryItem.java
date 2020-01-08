package com.princeakash.budgetmanager;

public class CategoryItem {
    public int id;
    public String categoryName;

    public CategoryItem(int id, String categoryName){
        this.categoryName = categoryName;
        this.id = id;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public int getId(){
        return id;
    }
}
