package org.example.scheduleapp.service;


import org.example.scheduleapp.entity.Writer;
import org.example.scheduleapp.repository.WriterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;

    public WriterServiceImpl(WriterRepository writerRepository) {

        this.writerRepository = writerRepository;
    }

    public Writer existCheckWriter(String name , String email) {

// 기존코드와 람다식 코드를 비교하여, 공부가 필요함 ( Prod 에는 적절하지 않은 주석 )
//        Optional<Writer> optional = writerRepository.findByNameAndEmail(name, email);
//
//        if (optional.isPresent()) {
//            return optional.get();
//        }
//
//        Writer writer = new Writer(name, email);
//
//        return writerRepository.save(writer);

        return writerRepository.findByNameAndEmail(name, email)
                .orElseGet(() -> writerRepository.save(new Writer(name, email)));
    }

    public Writer findWriterById(Long id) {

// 기존코드와 람다식 코드를 비교하여, 공부가 필요함 ( Prod 에는 적절하지 않은 주석 )
//        Optional<Writer> optional = writerRepository.findWriterById(id);
//
//        return optional.get();

        return writerRepository.findWriterById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Writer not found."));
    }
}
