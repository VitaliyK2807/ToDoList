package main;

import main.tasks.Task;
import main.tasks.TaskRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@RestController
public class Controller {

    @Autowired
    TaskRepository taskRepository;

    @RequestMapping("/datetime")
    public String getDateAndTime () {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return dateTime.format(dtf);
    }

    @RequestMapping("/numbers")
    public void getRandomNumber () {
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST)
    public ResponseEntity<?> setTask (@RequestParam String newTitle, String newDescription) {
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        Task task = new Task();
        task.setTitle(newTitle);
        task.setDescription(newDescription);
        task.setIsDone(getRandomIsDone());
        task.setCreationTime(LocalDateTime.now());
        taskRepository.save(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/tasks/{id}")
    public ResponseEntity<?> getTask (@PathVariable("id") int id) {
        Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

   private Boolean getRandomIsDone () {
        Random random = new Random();
        return random.nextBoolean();
   }

   @GetMapping(value = "/tasks")
   public ResponseEntity<?> getTasks () {
       List<Task> taskList = taskRepository.findAll();
       return new ResponseEntity<>(taskList, HttpStatus.OK);
   }
   @DeleteMapping(value = "/tasks/{id}")
   public ResponseEntity<?> deleteTask (@PathVariable("id") int id) {
       Optional<Task> task = taskRepository.findById(id);
        if (!task.isPresent()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
       taskRepository.delete(task.get());
       return new ResponseEntity<>(task, HttpStatus.OK);
   }

   @PatchMapping(value = "/tasks/{id}")
   public ResponseEntity<?> changedTask (@PathVariable("id")
                                         int id,
                                         String title,
                                         String description,
                                         Boolean isDone) {
       Optional<Task> task = taskRepository.findById(id);
       if (!task.isPresent()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       }
       if (!Strings.isEmpty(title)) {
           task.get().setTitle(title);
       }
       if (!Strings.isEmpty(description)) {
           task.get().setDescription(description);
       }
       if (isDone != null) {
           task.get().setIsDone(isDone);
       }
       taskRepository.save(task.get());
       return new ResponseEntity<>(task.get(), HttpStatus.CREATED);
   }
}
