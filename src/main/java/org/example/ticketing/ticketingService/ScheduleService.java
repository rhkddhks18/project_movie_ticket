package org.example.ticketing.ticketingService;

import org.example.ticketing.entity.Schedule;
import org.example.ticketing.ticketingRepository.ScheduleRepository;

import java.util.List;

public class ScheduleService {
    ScheduleRepository scheduleRepository = new ScheduleRepository();

    public List<Schedule> getAllSchedules(int movie_id) {
        return scheduleRepository.getAllSchedules(movie_id);
    }
}
