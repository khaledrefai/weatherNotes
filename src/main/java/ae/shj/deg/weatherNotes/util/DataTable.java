package ae.shj.deg.weatherNotes.util;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataTable<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int draw;

    private int start;

    private long recordsTotal;

    private long recordsFiltered;


    private List<T> data;

    public DataTable() {}
    public DataTable(int draw, int start, long recordsTotal, long recordsFiltered, List<T> data) {
        super();
        this.draw = draw;
        this.start = start;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    // ... getters and setters
}