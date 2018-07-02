package pl.harpi.samples.mssql.lock.rest;

import lombok.Data;

@Data
public class MsSqlRequest {
    private Long workTime;
    private String resource;
    private Integer timeout;
}
