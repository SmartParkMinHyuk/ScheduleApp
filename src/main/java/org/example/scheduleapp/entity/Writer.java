package org.example.scheduleapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Writer {

    private Long id;           // 고유 식별자(ID)
    private String name;       // 작성자명
    private String email;       // 작성자 이메일명
    private Date createdAt;       // 최초작성일
    private Date updatedAt;       // 최근수정일

    public Writer(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
