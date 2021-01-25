package ae.shj.deg.weatherNotes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "range")
public class Range {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "range_id")
    private int id;
    @Column(name = "mintemp")
    private int min;
    @Column(name = "maxtemp")
    private int max;

}
