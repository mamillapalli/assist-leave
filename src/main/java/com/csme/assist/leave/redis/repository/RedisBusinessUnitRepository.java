package com.csme.assist.leave.redis.repository;

import com.csme.assist.leave.redis.entity.RedisBusinessUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RedisBusinessUnitRepository extends CrudRepository<RedisBusinessUnit, String> {
}
