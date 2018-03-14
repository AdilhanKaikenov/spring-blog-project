package com.epam.adok.core.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Objects;

public class OffsetLimitPageRequest implements Pageable, Serializable {

    private int offset;
    private int limit;
    private final Sort sort;

    public OffsetLimitPageRequest(int offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than 0!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit index must not be less than 1!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public OffsetLimitPageRequest(int offset, int limit, Sort.Direction direction, String... properties) {
        this(offset, limit, new Sort(direction, properties));
    }

    public static OffsetLimitPageRequest of(int offset, int limit, Sort sort) {
        return new OffsetLimitPageRequest(offset, limit, sort);
    }

    public static OffsetLimitPageRequest of(int offset, int limit) {
        return of(offset, limit, Sort.unsorted());
    }

    public static OffsetLimitPageRequest of(int offset, int limit, Sort.Direction direction, String... properties) {
        return new OffsetLimitPageRequest(offset, limit, direction, properties);
    }

    @Override
    public int getPageNumber() {
        int pageCount = offset / limit;
        int residue = offset % limit;
        if (residue > 0) {
            pageCount++;
        }
        return pageCount;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetLimitPageRequest((int) (getOffset() + getPageSize()), getPageSize(), getSort());
    }

    public OffsetLimitPageRequest previous() {
        return hasPrevious() ? new OffsetLimitPageRequest((int) (getOffset() - getPageSize()), getPageSize(), getSort()) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetLimitPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffsetLimitPageRequest that = (OffsetLimitPageRequest) o;
        return offset == that.offset &&
                limit == that.limit &&
                Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, limit, sort);
    }
}
