package ru.jiehk.models;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;

@Data
public class UsersListResponse {
    private int page, per_page, total, total_pages;

    private ArrayList<UsersListData> data = new ArrayList<>();
    private UsersListSupport support = new UsersListSupport();
}
@Data
class UsersListData {
    private int id;
    private String email, first_name, last_name, avatar;
}

@Data
class UsersListSupport {
    private String url, text;
}
