package org.example.scheduleapp.service;

import org.example.scheduleapp.dto.ScheduleRequestDto;
import org.example.scheduleapp.dto.ScheduleResponseDto;
import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {

        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {

        Schedule schedule = new Schedule(requestDto.getName(), requestDto.getDescription(), requestDto.getPassword());

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {

        return scheduleRepository.findAllSchedules();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(optionalSchedule.get());
    }
}
