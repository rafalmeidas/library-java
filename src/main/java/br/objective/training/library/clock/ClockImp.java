package br.objective.training.library.clock;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClockImp implements Clock {
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
