package ru.jiehk.models;

import lombok.Data;

@Data
public class RegistrationSuccessfulResponse {
    private String token;
    private int id;
}
