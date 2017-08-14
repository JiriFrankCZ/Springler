package cz.jirifrank.app.springler.repository;

import cz.jirifrank.app.springler.model.entity.LogEntry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepository extends PagingAndSortingRepository<LogEntry, Long> {

}
