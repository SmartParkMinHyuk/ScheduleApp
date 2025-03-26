package org.example.scheduleapp.service.writer;

import org.example.scheduleapp.entity.Writer;

public interface WriterService {

    Writer existCheckWriter(String name , String email);

    Writer findWriterById(Long id);

}
