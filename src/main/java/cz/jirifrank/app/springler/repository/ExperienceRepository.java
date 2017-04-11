package cz.jirifrank.app.springler.repository;

import cz.jirifrank.app.springler.model.entity.Experience;
import cz.jirifrank.app.springler.model.statics.LearningAction;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends ElasticsearchCrudRepository<Experience, Long> {
	List<Experience> findAllByLearningAction(LearningAction learningAction);
}
