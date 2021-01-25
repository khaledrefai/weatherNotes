package ae.shj.deg.weatherNotes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PredefinedNotesId  implements Serializable {

    @Column(name = "admin_id")
    private int adminId;
    @Column(name = "range_id")
    private int rangeId;

}
