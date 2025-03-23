package org.example.scheduleapp.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;           // 고유 식별자(ID)
    private String name;       // 작성자명
    private String description; // 할일
    private String password;    // 비밀번호
    private Date createdAt;       // 작성일
    private Date updatedAt;       // 수정일

    public Schedule(String name, String description, String password) {

        this.name = name;
        this.description = description;
        this.password = password;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Schedule(Long id, String name, String description, Date createdAt, Date updatedAt) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
