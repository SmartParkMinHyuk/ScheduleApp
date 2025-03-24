package org.example.scheduleapp.repository;


import org.example.scheduleapp.dto.ScheduleResponseDto.*;
import org.example.scheduleapp.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Schedule saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer_id", schedule.getWriter_id());
        parameters.put("description", schedule.getDescription());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("updated_at", schedule.getUpdatedAt());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        schedule.setId(key.longValue());

        return schedule;
    }

    @Override
    public List<ScheduleRes> findAllSchedules() {

        return jdbcTemplate.query("SELECT \n" +
                "    s.id,\n" +
                "    s.writer_id,\n" +
                "    w.email,\n" +
                "    w.name,\n" +
                "    s.description,\n" +
                "    s.created_at,\n" +
                "    s.updated_at\n" +
                "FROM schedule s\n" +
                "JOIN writer w ON s.writer_id = w.id\n" +
                "ORDER BY s.created_at DESC;", scheduleRowMapper());

    }

    public List<ScheduleRes> findScheduleByWriterId(Long id) {
        return jdbcTemplate.query(
                "SELECT " +
                        "    s.id, " +
                        "    s.writer_id, " +
                        "    w.email, " +
                        "    w.name, " +
                        "    s.description, " +
                        "    s.created_at, " +
                        "    s.updated_at " +
                        "FROM schedule s " +
                        "JOIN writer w ON s.writer_id = w.id " +
                        "WHERE s.writer_id = ? " +
                        "ORDER BY s.created_at DESC;",
                scheduleRowMapper(),
                id
        );
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {

        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny();
    }


    @Override
    public int updateSchedule(Long id, Long writer_id, String name, String description) {

        return jdbcTemplate.update("update writer set name = ?, updated_at = NOW() where id = ?",name, writer_id) +
                jdbcTemplate.update("update schedule set description = ?, updated_at = NOW() where id = ?", description, id);
    }

    @Override
    public int deleteSchedule(Long id) {

        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    private RowMapper<ScheduleRes> scheduleRowMapper() {

        return new RowMapper<ScheduleRes>() {

            @Override
            public ScheduleRes mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleRes(
                        rs.getLong("id"),
                        rs.getLong("writer_id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at")
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {

        return new RowMapper<Schedule>() {

            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getLong("writer_id"),
                        rs.getString("description"),
                        rs.getString("password"),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at")
                );
            }
        };
    }
}
