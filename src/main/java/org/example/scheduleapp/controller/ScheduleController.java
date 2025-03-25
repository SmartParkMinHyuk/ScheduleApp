package org.example.scheduleapp.controller;


import org.example.scheduleapp.dto.ScheduleRequestDto;
import org.example.scheduleapp.dto.ScheduleRequestDto.*;
import org.example.scheduleapp.dto.ScheduleResponseDto.*;
import org.example.scheduleapp.dto.ScheduleResponseDto;
import org.example.scheduleapp.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
     * @return : {@link List <ScheduleCreateRes>} JSON 응답
     * @exception : @ExceptionHandler @Validated 값으로 응답
     **/
    @PostMapping
    public ResponseEntity<ScheduleRes> createSchedule(
            @RequestBody final @Validated ScheduleCreateReq reqDto
    ) {
        return new ResponseEntity<>(scheduleService.saveSchedule(reqDto), HttpStatus.CREATED);
    }

    /**
     * 스케줄 전체 조회 API
     * @return : {@link List <ScheduleRes>} JSON 응답
     */
    @GetMapping
    public ResponseEntity<List<ScheduleRes>> findAllSchedules() {

        return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
    }

    /**
     * 작성자 고유 식별자를 통한 스케줄 조회 API
     * @param writer_id 식별자
     * @return : {@link List <ScheduleRes>} JSON 응답
     */
    @GetMapping("/writer/{writer_id}")
    public ResponseEntity<List<ScheduleRes>> findScheduleByWriterId(@PathVariable Long writer_id) {

        return new ResponseEntity<>(scheduleService.findScheduleByWriterId(writer_id), HttpStatus.OK);
    }

    /**
     * 스케줄 할일, 작성자명 수정 API
     * @param id 식별자
     * @param : {@link ScheduleRequestDto} 메모 수정 요청 객체
     * @return : {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     * @exception ResponseStatusException
     * 요청 필수값이 없는 경우 400 Bad Request
     * 식별자로 조회된 스케줄이 없는 경우 404 Not Found
     * 비밀번호가 다르다면 UNAUTHORIZED, Incorrect password
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleRes> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateReq reqDto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, reqDto), HttpStatus.OK);
    }

    /**
     * 스케줄 삭제 API
     * @param id 식별자
     * @return {@link ResponseEntity<Void>} 성공시 Data 없이 200OK 상태코드만 응답.
     * @exception ResponseStatusException 식별자로 조회된 스케줄이 없는 경우 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleDeleteReq reqDto
    ) {
        scheduleService.deleteSchedule(id, reqDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 페이징처리
     */
    @GetMapping("/{paged}")
    public ResponseEntity<PageResponseDto<ScheduleRes>> findPagedSchedules(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageResponseDto<ScheduleRes> schedulePage = scheduleService.findPagedSchedules(page, size);
        return ResponseEntity.ok(schedulePage);
    }
}
