package org.example.scheduleapp.controller;


import org.example.scheduleapp.dto.ScheduleRequestDto;
import org.example.scheduleapp.dto.ScheduleResponseDto;
import org.example.scheduleapp.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 생성자 주입
     * 클래스가 필요로 하는 의존성을 생성자를 통해 전달하는 방식
     */
    public ScheduleController(ScheduleService scheduleService) {

        this.scheduleService = scheduleService;
    }

    /**
     * 스케줄 생성 API
     * @return : {@link List <ScheduleResponseDto>} JSON 응답
     **/
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {

        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }

    /**
     * 스케줄 전체 조회 API
     * @return : {@link List <ScheduleResponseDto>} JSON 응답
     */
    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules() {

        return scheduleService.findAllSchedules();
    }

    /**
     * 스케줄 ID 단건 조회 API
     * @param id 식별자
     * @return : {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

}
