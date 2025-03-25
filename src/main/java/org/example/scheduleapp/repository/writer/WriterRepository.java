package org.example.scheduleapp.repository.writer;


import org.example.scheduleapp.entity.Writer;

import java.util.Optional;

public interface WriterRepository {

    Optional<Writer> findByNameAndEmail(String name, String email);

    Writer save(Writer writer);

    Optional<Writer> findWriterById(Long id);
}
