package ae.shj.deg.weatherNotes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_id")
    private int noteId;

    @Column(name="note")
    @Length(min = 5, message = "*Note must have at least 5 characters")
    @NotEmpty(message = "*Please write your note")
    private String note;

    @Column(name = "admin_id")
    private int adminId;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy" ,  timezone =  "Asia/Dubai" )
    @Temporal(TemporalType.DATE)
    @Column(name = "note_date")
    @NotNull(message = "*Please select date")
    private Date noteDate;

    @ManyToOne(optional = false,fetch = FetchType.EAGER )
    @JoinColumn(name = "admin_id", updatable = false, insertable = false)
    private User user;

}
