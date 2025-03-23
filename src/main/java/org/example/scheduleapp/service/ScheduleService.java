package org.example.scheduleapp.service;

import org.example.scheduleapp.dto.ScheduleRequestDto;
import org.example.scheduleapp.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedules();

    ScheduleResponseDto findScheduleById(Long id);

}
