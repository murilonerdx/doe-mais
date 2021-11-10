package com.murilonerdx.doemais.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.inject.DozerBeanContainer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class DozerConverter {
    public static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O,D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin,destination);
    }

    public static <O,D> List<D> parseListObjects(List<O> origin, Class<D> destination){
        List<D> destinationObjects = new ArrayList<D>();
        for(Object o: origin){
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}
