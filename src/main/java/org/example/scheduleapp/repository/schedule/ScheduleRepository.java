package org.example.scheduleapp.repository.schedule;


import org.example.scheduleapp.dto.ScheduleResponseDto.*;
import org.example.scheduleapp.entity.Schedule;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);

    List<ScheduleRes> findAllSchedules();

    List<ScheduleRes> findScheduleByWriterId(Long id);

    Optional<Schedule> findScheduleById(Long id);

    int updateSchedule(Long id, Long writer_id, String name, String description);

    int deleteSchedule(Long id);

    List<ScheduleRes> findPagedSchedules(int offset, int size);

    long countSchedules();
}
