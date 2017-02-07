package ru.geekbrain.gbseeker.personrank.net;

public interface iNet2SQL {
    public void updateDB(String json,int id);
    public void updateUI();
    public String getInfo();
}
