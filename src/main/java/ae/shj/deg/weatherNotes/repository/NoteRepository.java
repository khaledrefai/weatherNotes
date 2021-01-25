package ae.shj.deg.weatherNotes.repository;

import ae.shj.deg.weatherNotes.model.Note;
import ae.shj.deg.weatherNotes.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findByNoteDate(Date noteDate);
    @Query(value = "select n from Note  n where n.user = :admin")
    Page<Note> findByAdmin(@Param("admin") User admin, Pageable pageable);

    @Query(value = "select n from Note n where  n.user = :admin and trunc(n.noteDate) = trunc(:noteDate)")
    Optional<Note>  getNoteByDateAndAdmin(@Param("admin") User admin, @Param("noteDate") Date notDate);
}
