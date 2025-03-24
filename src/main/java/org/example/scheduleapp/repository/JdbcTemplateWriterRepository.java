package org.example.scheduleapp.repository;


import org.example.scheduleapp.entity.Writer;
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
public class JdbcTemplateWriterRepository implements WriterRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateWriterRepository (DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Writer> findByNameAndEmail(String name, String email) {

        List<Writer> result = jdbcTemplate.query("select * from writer where name = ? and email = ?", writerRowMapper(), name, email);

        return result.stream().findAny();
    }

    @Override
    public Writer save(Writer writer) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("writer").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", writer.getName());
        parameters.put("email", writer.getEmail());
        parameters.put("created_at", writer.getCreatedAt());
        parameters.put("updated_at", writer.getUpdatedAt());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new Writer(
                key.longValue(),
                writer.getName(),
                writer.getEmail(),
                writer.getCreatedAt(),
                writer.getUpdatedAt()
        );
    }

    @Override
    public Optional<Writer> findWriterById(Long id) {

        List<Writer> result = jdbcTemplate.query("select * from writer where id = ?", writerRowMapper(), id);

        return result.stream().findAny();
    }

    private RowMapper<Writer> writerRowMapper() {

        return new RowMapper<Writer>() {

            @Override
            public Writer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Writer(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at")
                );
            }
        };
    }
}
