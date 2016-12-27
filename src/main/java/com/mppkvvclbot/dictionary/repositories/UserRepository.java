package com.mppkvvclbot.dictionary.repositories;

import com.mppkvvclbot.dictionary.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by NITISH on 28-12-2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
