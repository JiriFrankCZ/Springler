package cz.jirifrank.app.springler.repository;

import cz.jirifrank.app.springler.model.entity.Experience;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends CrudRepository<Experience, Long>{
}
