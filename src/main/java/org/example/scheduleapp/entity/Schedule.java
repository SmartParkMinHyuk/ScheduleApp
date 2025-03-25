package org.example.scheduleapp.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Schedule {

    private Long id;           // 고유 식별자(ID)
    private Long writer_id;           // 작성자 고유 식별자(ID)
    private String description; // 할일
    private String password;    // 비밀번호
    private Date createdAt;       // 작성일
    private Date updatedAt;       // 수정일

    public Schedule(Long writer_id, String description, String password) {

        this.writer_id = writer_id;
        this.description = description;
        this.password = password;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

}
