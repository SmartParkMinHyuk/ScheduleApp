package org.example.scheduleapp.dto;

import lombok.*;
import org.example.scheduleapp.entity.Schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.example.scheduleapp.entity.Writer;

public class ScheduleResponseDto {

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ScheduleRes {

        protected Long id;    // 고유 식별자(Schedule_id)
        protected Long writer_id;      // 고유 식별자(Writer_id)
        protected String email;       // 이메일명
        protected String name;        // 작성자명
        protected String description; // 할일
        protected Date createdAt;     // 작성일
        protected Date updatedAt;     // 수정일

        @Builder
        public ScheduleRes(Writer writer, Schedule schedule) {
            if (ObjectUtils.isNotEmpty(schedule) || ObjectUtils.isNotEmpty(writer)) {
                this.id = schedule.getId();
                this.writer_id = writer.getId();
                this.email = writer.getEmail();
                this.name = writer.getName();
                this.description = schedule.getDescription();
                this.createdAt = schedule.getCreatedAt();
                this.updatedAt = schedule.getUpdatedAt();
            }
        }

        public ScheduleRes(Long id, Long writer_id, String email, String name, String description, Date createdAt, Date updatedAt) {
            this.id = id;
            this.writer_id = writer_id;
            this.email = email;
            this.name = name;
            this.description = description;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }

    @Getter
    @Builder
    public static class PageResponseDto<T> {
        private int currentPage;  // 현재 페이지 번호
        private int pageSize;     // 한 페이지 당 데이터 수
        private long totalItems;  // 전체 데이터 건수
        private int totalPages;   // 총 페이지 수
        private List<T> data;     // 현재 페이지에 해당하는 데이터 목록
    }

}
