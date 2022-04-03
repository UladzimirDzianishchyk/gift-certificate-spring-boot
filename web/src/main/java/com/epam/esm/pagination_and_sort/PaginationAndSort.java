package com.epam.esm.pagination_and_sort;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class PaginationAndSort<T> extends RepresentationModel<PaginationAndSort<T>> {

    public PaginationAndSort(int currentPage, int maxResult, String findBy, String sort) {
        this.currentPage = currentPage;
        this.maxResult = maxResult;
        this.findBy = findBy;
        this.sort = sort;
    }

    public PaginationAndSort(int currentPage, int maxResult) {
        this.currentPage = currentPage;
        this.maxResult = maxResult;
    }

    public PaginationAndSort() {
    }

    private int currentPage;
    private int totalPage;
    private int maxResult;
    private String sort;
    private String findBy;
    private List<T> resultList;



    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getFindBy() {
        return findBy;
    }

    public void setFindBy(String findBy) {
        this.findBy = findBy;
    }

}
