package com.fenglin.domain;

import lombok.Data;

@Data
public class Bookorder {
    int id;
    public String orderid;
    public int userid;
    public int bookid;
    public int num;
    public String name;
    public String phone;
    public String addr;
    public String cod = "未支付";
    public transient Book book;

    public Bookorder(){}
    public Bookorder(String orderid, int userid, int bookid, int num, String name, String phone, String addr){
        this.orderid=orderid;
        this.userid=userid;
        this.bookid=bookid;
        this.num=num;
        this.name=name;
        this.phone=phone;
        this.addr=addr;
        this.cod=cod;
    }
    public Bookorder(String orderid, int userid, int bookid, int num, String name, String phone, String addr, String cod){
        this.orderid=orderid;
        this.userid=userid;
        this.bookid=bookid;
        this.num=num;
        this.name=name;
        this.phone=phone;
        this.addr=addr;
        this.cod=cod;
    }
    public Bookorder(String name, String phone, String addr){
        this.name=name;
        this.phone=phone;
        this.addr=addr;

    }
}
