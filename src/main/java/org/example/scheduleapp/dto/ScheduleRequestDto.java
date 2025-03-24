package org.example.scheduleapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ScheduleRequestDto {

    @Data
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class ScheduleCreateReq {

        @NotBlank(message = "name required")
        protected String name;

        @NotBlank(message = "email required")
        @Email(message = "Invalid email format")
        protected String email;

        @NotBlank(message = "description required")
        @Size(max = 200, message = "description must be less than or equal to 200 characters")
        protected String description;

        @NotBlank(message = "password required")
        protected String password;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class ScheduleUpdateReq {

        @NotBlank(message = "name required")
        protected String name;

        @NotBlank(message = "description required")
        @Size(max = 200, message = "description must be less than or equal to 200 characters")
        protected String description;

        @NotBlank(message = "password required")
        protected String password;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    public static class ScheduleDeleteReq {

        @NotBlank(message = "password required")
        protected String password;
    }

}
