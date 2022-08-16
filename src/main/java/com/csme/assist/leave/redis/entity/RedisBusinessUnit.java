package com.csme.assist.leave.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash("BusinessUnit")
public class RedisBusinessUnit implements Serializable {

    @Id
    private String name;
    private String defaultCurrency;
}
