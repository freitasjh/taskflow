package br.com.systec.taskflow.commons.filter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class FilterPageParam<T> {

    protected String keyword;
    protected int limit;
    protected int page;
    protected Set<String> orderBy;
    protected String orderByDirection;

    protected FilterPageParam() {
        this.keyword = "";
        this.limit = 30;
        this.page = 0;
        this.orderBy = new HashSet<>();
        this.orderByDirection = "ASC";
    }

    protected FilterPageParam(int limit, int page, String keyword) {
        this.limit = limit;
        this.page = page;
        this.keyword = keyword;
        this.orderBy = new HashSet<>();
        this.orderByDirection = "ASC";
    }

    public abstract Specification<T> getSpecification();

    public Pageable getPageable() {
        if (!orderBy.isEmpty()) {
            String[] array = orderBy.toArray(new String[0]);
            return PageRequest.of(page, limit, createOrderBy(Sort.Direction.fromString(orderByDirection), array));
        }

        return PageRequest.of(page, limit);
    }

    public String getKeyword() {
        return Optional.ofNullable(keyword).orElse("");
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setOrderByDirection(String orderByDirection) {
        this.orderByDirection = orderByDirection;
    }

    public String getOrderByDirection() {
        return orderByDirection;
    }

    public Sort createOrderBy(Sort.Direction direction, String... args) {
        return Sort.by(direction, args);
    }


    public void addOrderBy(String order) {
        if (order != null && !order.isEmpty()) {
            if(order.contains(";")){
                String[] orders = order.split(";");
                this.orderBy.addAll(Arrays.asList(orders));
            } else {
                this.orderBy.add(order);
            }
        }
    }
}