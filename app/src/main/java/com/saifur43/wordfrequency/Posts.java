package com.saifur43.wordfrequency;

public class Posts {

    String p_name;
    String links;

    public Posts(){

    }

    public Posts(String p_name, String links){
        this.p_name = p_name;
        this.links = links;

    }

    public String getP_name(){
        return p_name;

    }

    public String getLinks() {
        return links;
    }
}
