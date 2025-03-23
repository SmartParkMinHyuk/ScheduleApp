package org.example.scheduleapp.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private String name;       // 작성자명
    private String description; // 할일
    private String password;    // 비밀번호
}
