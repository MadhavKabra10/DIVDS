package com.btp.ccproject.repo;

import com.btp.ccproject.Model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image,String> {
}
