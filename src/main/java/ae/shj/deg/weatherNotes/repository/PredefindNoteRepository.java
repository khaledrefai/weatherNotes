package ae.shj.deg.weatherNotes.repository;

import ae.shj.deg.weatherNotes.model.PredefinedNotes;
import ae.shj.deg.weatherNotes.model.PredefinedNotesId;
import ae.shj.deg.weatherNotes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface PredefindNoteRepository  extends JpaRepository<PredefinedNotes, PredefinedNotesId> {
    @Query(value = "select  p.* from predefined_notes p where p.admin_id not in " +
            " ( select admin_id from note where note_date = trunc(sysdate) ) " +
            "and p.range_id in (select  range_id from range where mintemp < :temperature  " +
            "           and  maxtemp >= :temperature )",nativeQuery = true)
     List<PredefinedNotes> getTodayPrededindNotes(@Param("temperature") BigDecimal temperature);

    @Query("select p from PredefinedNotes p where p.predefinedNotesId.adminId = ?1")
    List<PredefinedNotes> findByAdmin(int adminId);

}
