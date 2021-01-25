package ae.shj.deg.weatherNotes.controller;

import ae.shj.deg.weatherNotes.model.*;
import ae.shj.deg.weatherNotes.service.NoteService;
import ae.shj.deg.weatherNotes.service.UserService;
import ae.shj.deg.weatherNotes.service.WeatherService;
import ae.shj.deg.weatherNotes.util.DataTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final NoteService noteService;
    private final WeatherService weatherService;
    private final UserService userService;

    Logger logger = LoggerFactory.getLogger(MainController.class);


    @Autowired
    public  MainController(NoteService noteService,WeatherService weatherService,UserService userService){
        this.noteService = noteService;
        this.weatherService = weatherService;
        this.userService= userService;
        logger.info("initializing Main Controller");
    }
    @GetMapping("/home")
    public String getUserPage(Model model){
        try {
            CurrentWeather currentWeather = weatherService.getCurrentWeather();
            model.addAttribute("noteList", noteService.getTodaySystemNotes());
            model.addAttribute("currentWeather", currentWeather);
            model.addAttribute("predefinedNotesList", noteService.getTodayPredefinedNotes(currentWeather.getTemperature()));
        }catch (Exception e){
            logger.error("Error in getUserPage ",e);
        }
        return "home";
    }

    @GetMapping("admin/notes")
    public String getAdminPage(){
         return "admin/notes";
    }
    @GetMapping("admin/pre_notes")
    public String getAdminPreNotes(Model model, Authentication auth,PredefinedNotes predefinedNotes ){
        try {
            User admin = userService.findUserByUserName(auth.getName());

            model.addAttribute("rangeList", noteService.getRangeList());
            model.addAttribute("myPredefinedNotesList", noteService.getPredefinedNotesByAdmin(admin));
        }catch (Exception e){
            logger.error("error in get pre admin note",e);
        }
        return "admin/pre_notes";
    }
    @PostMapping("admin/saveprenotes")
    public String savedminPreNotes(@Valid PredefinedNotes predefinedNotes ,Model model, Authentication auth,
                                   BindingResult bindingResult ){
        User admin = userService.findUserByUserName(auth.getName());
        predefinedNotes.getPredefinedNotesId().setAdminId(admin.getId());

        noteService.addPredefindNote(predefinedNotes);
        if (bindingResult.hasErrors()) {
            return  "admin/pre_notes" ;
        }

        return "redirect:/admin/pre_notes";
    }
    @ResponseBody
    @PostMapping("admin/api/note/save")
    public ResponseEntity saveNote(@Valid @RequestBody Note note, BindingResult bindingResult
            , Authentication auth){

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            // Here you can change ok to badRequest depending on your use case.
            //return ResponseEntity.ok(new ErrorResponse("404", "Validation failure", errors));
            // In case if you want to fail the request, you need to use the below:

            return ResponseEntity.badRequest().body(errors);
        }
        User admin = userService.findUserByUserName(auth.getName());
        note.setUser(admin);
        note.setAdminId(admin.getId());
        Note existnote = noteService.getExistNote(note).orElse(null);
        if (existnote != null) {
            existnote.setNote(note.getNote());
            noteService.addAdminNote(existnote);
        }else {
            noteService.addAdminNote(note);
        }
    return ResponseEntity.ok("");
    }

    @ResponseBody
    @PostMapping("admin/api/note/search")
    public ResponseEntity<DataTable<Note>> getAdminNotes(@RequestParam("draw") int draw,
    @RequestParam("start") int start, @RequestParam("length") int length ,Authentication auth ){
        User admin = userService.findUserByUserName(auth.getName());
        Page<Note> responseData = noteService.getNoteByAdmin(admin ,start , length);

        DataTable<Note> dataTable = new DataTable<Note>();
        dataTable.setData(responseData.getContent());
        dataTable.setRecordsTotal(responseData.getTotalElements());
        dataTable.setRecordsFiltered(responseData.getTotalElements());
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        return ResponseEntity.ok(dataTable);
    }
    @ResponseBody
    @DeleteMapping  ("admin/api/note/delete")
    public ResponseEntity deleteNote(@RequestParam int noteId  ,Authentication auth  ){
        User admin = userService.findUserByUserName(auth.getName());
        // future work check if this admin owen this note

            noteService.deleteNote(noteId);

        return ResponseEntity.ok("");
    }
}
