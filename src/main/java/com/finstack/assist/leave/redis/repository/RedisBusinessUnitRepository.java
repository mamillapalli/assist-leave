package com.finstack.assist.leave.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.finstack.assist.leave.redis.entity.RedisBusinessUnit;


@Repository
public interface RedisBusinessUnitRepository extends CrudRepository<RedisBusinessUnit, String> {
}
