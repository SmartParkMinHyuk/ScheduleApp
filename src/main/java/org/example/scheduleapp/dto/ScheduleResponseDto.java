package org.example.scheduleapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.scheduleapp.entity.Schedule;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;           // 고유 식별자(ID)
    private String name;       // 작성자명
    private String description; // 할일
    private Date createdAt;       // 작성일
    private Date updatedAt;       // 수정일

    public ScheduleResponseDto(Schedule schedule) {

        this.id = schedule.getId();
        this.name = schedule.getName();
        this.description = schedule.getDescription();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
