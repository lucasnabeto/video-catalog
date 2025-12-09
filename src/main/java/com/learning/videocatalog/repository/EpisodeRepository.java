package com.learning.videocatalog.repository;

import com.learning.videocatalog.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
