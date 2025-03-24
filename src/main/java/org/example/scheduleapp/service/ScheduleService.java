package org.example.scheduleapp.service;


import org.example.scheduleapp.dto.ScheduleRequestDto.*;
import org.example.scheduleapp.dto.ScheduleResponseDto.*;

import java.util.List;

public interface ScheduleService {

    ScheduleRes saveSchedule(ScheduleCreateReq reqDto);

    List<ScheduleRes> findAllSchedules();

    List<ScheduleRes> findScheduleByWriterId(Long id);

    ScheduleRes updateSchedule(Long id, ScheduleUpdateReq reqDto);

    void deleteSchedule(Long id, ScheduleDeleteReq reqDto);

}
