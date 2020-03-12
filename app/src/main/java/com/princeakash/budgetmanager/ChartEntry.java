package com.princeakash.budgetmanager;

public class ChartEntry {
    int amount;
    String str;

    public ChartEntry(int amount, String str){
        this.amount = amount;
        this.str = str;
    }

    public int getAmount() {
        return amount;
    }

    public String getStr() {
        return str;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
