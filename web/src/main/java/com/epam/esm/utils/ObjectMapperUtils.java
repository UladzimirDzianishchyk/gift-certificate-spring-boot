package com.epam.esm.utils;

import com.epam.esm.pagination_and_sort.PaginationAndSort;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapperUtils {

    private static ModelMapper modelMapper = new ModelMapper();


    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }


    private ObjectMapperUtils() {
    }

    //Map Object
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    //Map ResultList
    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    //Map PaginationAndSort
    public static <D, T> PaginationAndSort<D> convert(PaginationAndSort<T> p2){
        PaginationAndSort<D> result = new PaginationAndSort<>();
        result.setMaxResult(p2.getMaxResult());
        result.setCurrentPage(p2.getCurrentPage());
        result.setTotalPage(p2.getTotalPage());
        result.setFindBy(p2.getFindBy());
        result.setSort(p2.getSort());

        return result;
    }
}

