package vn.techmaster.lesson3hw.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import vn.techmaster.lesson3hw.dto.JobRequest;
import vn.techmaster.lesson3hw.model.Job;
import vn.techmaster.lesson3hw.model.Location;

@RestController
@RequestMapping("/job")
public class JobController {
    private ConcurrentHashMap<String, Job> jobs;

    public JobController() {
        jobs = new ConcurrentHashMap<>();

    }

    @GetMapping
    public List<Job> getJobs() {
        return jobs.values().stream().toList();
    }

    @PostMapping
    public ResponseEntity<Job> createNewJob(@RequestBody JobRequest jobRequest) {
        String uuid = UUID.randomUUID().toString();
        Job newJob = new Job(uuid, jobRequest.title(), jobRequest.description(), jobRequest.location(),
                jobRequest.min_salary(), jobRequest.max_salary(), jobRequest.email_to());
        jobs.put(uuid, newJob);
        return ResponseEntity.status(HttpStatus.CREATED).body(newJob);
    }

    @GetMapping(value = "/{id}")
    public Job getJobById(@PathVariable("id") String id) {
        Job job = jobs.get(id);
        if (job == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        return job;
    }

    @PutMapping(value = { "/id" })
    public Job replaceJob(@PathVariable("id") String id, @RequestBody JobRequest jobRequest) {
        Job job = jobs.get(id);
        if (job == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        Job updateJob = new Job(id, jobRequest.title(), jobRequest.description(), jobRequest.location(),
                jobRequest.min_salary(), jobRequest.max_salary(), jobRequest.email_to());
        jobs.replace(id, updateJob);
        return updateJob;
    }

    @DeleteMapping(value = "/{id}")
    public Job deleteJobById(@PathVariable("id") String id) {
        Job removedJob = jobs.remove(id);
        return removedJob;
    }

    @GetMapping(value = "/sortbylocation")
    public List<Job> sortLocation() {
        List<Job> job = jobs.values().stream().toList();
        return job.stream().sorted(Comparator.comparing(Job::getLocation)).collect(Collectors.toList());
    }

    @GetMapping(value = "/salary/{salary}")
    public List<Job> jobWithinSalaryRange(@PathVariable(value = "salary") int salary) {
        List<Job> job = jobs.values().stream().toList();
        List<Job> salaryRangeJob = new ArrayList<>();
        for (Job job2 : job) {
            if (job2.getMax_salary() > salary && job2.getMin_salary() < salary) {
                salaryRangeJob.add(job2);
            }
        }
        return salaryRangeJob;
    }

    @GetMapping(value = "/keyword/{keyword}")
    public List<Job> jobWithKeyword(@PathVariable(value = "keyword") String keyword) {
        List<Job> job = jobs.values().stream().toList();
        List<Job> jobKeyword = new ArrayList<>();
        for (Job job2 : job) {
            if (job2.getDescription().contains(keyword) || job2.getTitle().contains(keyword)) {
                jobKeyword.add(job2);
            }
        }
        return jobKeyword;
    }

    @GetMapping(value = "query?location={location}&keyword={keyword}")
    public List<Job> queryJob(@PathVariable("location") Location location, @PathVariable("keyword") String keyword) {
        List<Job> job = jobs.values().stream().toList();
        List<Job> queryJobList = new ArrayList<>();
        for (Job job2 : job) {
            if (job2.getDescription().contains(keyword) || job2.getTitle().contains(keyword)) {
                if (job2.getLocation() == location) {
                    queryJobList.add(job2);
                }
            }
        }
        return queryJobList;
    }
}
