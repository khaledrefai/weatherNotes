package ae.shj.deg.weatherNotes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "predefined_notes")
public class PredefinedNotes {

    @EmbeddedId
    private PredefinedNotesId predefinedNotesId;

    @Column(name = "note")
    @NotEmpty(message = "*Please provide note")
    private String note;

    @ManyToOne(optional = false,fetch = FetchType.EAGER )
    @JoinColumn(name = "admin_id", updatable = false, insertable = false)
    private User user;

    @ManyToOne(optional = false,fetch = FetchType.EAGER )
    @JoinColumn(name = "range_id", updatable = false, insertable = false)
    private Range range;
}
