package vn.techmaster.lesson3hw.dto;

import vn.techmaster.lesson3hw.model.Location;

public record JobRequest(
        String title,
        String description,
        Location location,
        int min_salary,
        int max_salary,
        String email_to) {
}
