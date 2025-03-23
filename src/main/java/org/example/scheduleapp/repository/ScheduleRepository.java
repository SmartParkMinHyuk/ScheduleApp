package org.example.scheduleapp.repository;

import org.example.scheduleapp.dto.ScheduleResponseDto;
import org.example.scheduleapp.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();

    Optional<Schedule> findScheduleById(Long id);

    int updateSchedule(Long id, String name, String description);

    int deleteSchedule(Long id);
}
