package de.amthor.gendb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
