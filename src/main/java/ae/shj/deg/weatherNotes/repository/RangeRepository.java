package ae.shj.deg.weatherNotes.repository;

import ae.shj.deg.weatherNotes.model.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RangeRepository extends JpaRepository<Range, Integer> {
}
