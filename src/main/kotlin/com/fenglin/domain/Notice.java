package com.fenglin.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Notice {
    public Integer id;
    public String title;
    public String content;
    public Date time;
    public transient Long cod;
}
