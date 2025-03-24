package org.example.scheduleapp.service;


import org.example.scheduleapp.dto.ScheduleResponseDto.*;
import org.example.scheduleapp.entity.Schedule;
import org.example.scheduleapp.entity.Writer;
import org.example.scheduleapp.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.example.scheduleapp.dto.ScheduleRequestDto.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final WriterService writerService;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, WriterService writerService) {
        this.scheduleRepository = scheduleRepository;
        this.writerService = writerService;
    }

    @Transactional
    @Override
    public ScheduleRes saveSchedule(ScheduleCreateReq reqDto) {

        Writer writer = writerService.existCheckWriter(reqDto.getName(), reqDto.getEmail());

        Schedule schedule = new Schedule(writer.getId(), reqDto.getDescription(), reqDto.getPassword());

        schedule = scheduleRepository.saveSchedule(schedule);

        ScheduleRes scheduleRes = ScheduleRes.builder()
                                                .writer(writer)
                                                .schedule(schedule)
                                                .build();

        return scheduleRes;
    }

    @Override
    public List<ScheduleRes> findAllSchedules() {

        return scheduleRepository.findAllSchedules();
    }

    @Override
    public List<ScheduleRes> findScheduleByWriterId(Long id) {

        List<ScheduleRes> ScheduleResList = scheduleRepository.findScheduleByWriterId(id);

        if (ScheduleResList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist writer_id = " + id);
        }

        return ScheduleResList;
    }

    @Transactional
    @Override
    public ScheduleRes updateSchedule(Long id, ScheduleUpdateReq reqDto) {

        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found."));

        if (!schedule.getPassword().equals(reqDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password.");
        }

        int updatedRow = scheduleRepository.updateSchedule(id, schedule.getWriter_id(), reqDto.getName(), reqDto.getDescription());

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "No data has been modified.");
        }

        Writer writer = writerService.findWriterById(schedule.getWriter_id());

        return new ScheduleRes(writer, schedule);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id, ScheduleDeleteReq reqDto) {

//        Optional<Schedule> scheduleOptional = scheduleRepository.findScheduleById(id);
//        Schedule schedule;
//        if (scheduleOptional.isPresent()) {
//            schedule = scheduleOptional.get();
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found.");
//        }
// 기존 코드 --> 람다식으로 변환 람다식 변환 공부가 필요함. (Prod 적절하지 않은 주석)

        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found."));

        if (!schedule.getPassword().equals(reqDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password.");
        }

        int deletedRow = scheduleRepository.deleteSchedule(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }



    }
}
