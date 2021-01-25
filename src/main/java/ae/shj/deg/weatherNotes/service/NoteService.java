package ae.shj.deg.weatherNotes.service;

import ae.shj.deg.weatherNotes.controller.MainController;
import ae.shj.deg.weatherNotes.model.Note;
import ae.shj.deg.weatherNotes.model.PredefinedNotes;
import ae.shj.deg.weatherNotes.model.Range;
import ae.shj.deg.weatherNotes.model.User;
import ae.shj.deg.weatherNotes.repository.NoteRepository;
import ae.shj.deg.weatherNotes.repository.PredefindNoteRepository;
import ae.shj.deg.weatherNotes.repository.RangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

  private final NoteRepository noteRepository;
  private  final  PredefindNoteRepository predefindNoteRepository;
  private final RangeRepository rangeRepository;

    Logger logger = LoggerFactory.getLogger(MainController.class);


  @Value("${resourceAlreadyExists}")
  private Integer resourceAlreadyExists;

    @Value("${success}")
    private Integer success;

    @Autowired
    public NoteService(NoteRepository noteRepository , PredefindNoteRepository predefindNoteRepository,
                       RangeRepository rangeRepository){
        this.noteRepository=noteRepository;
        this.predefindNoteRepository=predefindNoteRepository;
        this.rangeRepository = rangeRepository;
        logger.info("initializing Note Service");
    }

    public List<Note> getTodaySystemNotes(){
        Date today = new Date();
        List<Note>  noteList = noteRepository.findByNoteDate(today);
        logger.info("call getTodaySystemNotes ");
      return noteList;
    }

    public List<PredefinedNotes> getTodayPredefinedNotes(BigDecimal temperature){
       List<PredefinedNotes> predefinedNotesList= predefindNoteRepository.getTodayPrededindNotes( temperature);
       return predefinedNotesList;
    }
    public void addAdminNote(Note note){
            noteRepository.save(note);
    }
    public Optional<Note> getExistNote(Note note){
       return  noteRepository.getNoteByDateAndAdmin(note.getUser() , note.getNoteDate());
    }
    public void addPredefindNote(PredefinedNotes predefinedNotes){
        PredefinedNotes predefinedNotes1 = predefindNoteRepository.findById(predefinedNotes.getPredefinedNotesId()).orElse(null);
        if(predefinedNotes1 !=null) {
            predefinedNotes1.setNote(predefinedNotes.getNote());
            predefindNoteRepository.saveAndFlush(predefinedNotes1);
        }else
            predefindNoteRepository.saveAndFlush(predefinedNotes);
    }



    public List<PredefinedNotes> getPredefinedNotesByAdmin(User admin){
        List<PredefinedNotes> predefinedNotesList= predefindNoteRepository.findByAdmin(admin.getId());
        return predefinedNotesList;
    }

    public List<Range> getRangeList(){
     return   rangeRepository.findAll();
    }
    public void deleteNote(int noteId){
        noteRepository.deleteById(noteId);
    }
    public Page<Note> getNoteByAdmin(User admin,int start , int length){
        int page = start / length;
        Pageable pageable = PageRequest.of(page, length ,Sort.by("noteDate").descending() );
        Page<Note> notePage =noteRepository.findByAdmin(admin,pageable);
        return notePage;
    }
}
