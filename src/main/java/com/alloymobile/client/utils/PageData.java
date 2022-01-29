package com.alloymobile.client.utils;

import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PageData {
    private final Integer pageNumber;
    private final Integer pageSize;

    public PageData(Environment environment) {
        this.pageNumber = Integer.parseInt(Objects.requireNonNull(environment.getProperty("client.page-number")));
        this.pageSize = Integer.parseInt(Objects.requireNonNull(environment.getProperty("client.page-size")));
    }

    public Pageable getPage(Integer page,Integer size,String sort){
        Integer pageNumber = this.pageNumber;
        Integer pageSize = this.pageSize;

        if(Objects.nonNull(page))
            pageNumber = page;
        if(Objects.nonNull(size))
            pageSize = size;

        if(Objects.nonNull(sort) && !sort.equalsIgnoreCase("")){
            String[] sortParameter = sort.split(",");
            if(sortParameter.length == 2){
                if(sortParameter[1].equalsIgnoreCase("asc")){
                    return PageRequest.of(pageNumber,pageSize, Sort.Direction.ASC,sortParameter[0]);
                }else if(sortParameter[1].equalsIgnoreCase("desc")){
                    return PageRequest.of(pageNumber,pageSize, Sort.Direction.DESC,sortParameter[0]);
                }else{
                    return PageRequest.of(pageNumber,pageSize,Sort.Direction.ASC,"id");
                }
            }
        }
        return PageRequest.of(pageNumber,pageSize,Sort.Direction.ASC,"id");
    }
}
