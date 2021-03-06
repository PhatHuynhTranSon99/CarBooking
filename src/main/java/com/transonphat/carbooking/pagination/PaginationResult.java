package com.transonphat.carbooking.pagination;

/**
 * Author: Tran Son Phat
 * Wrap the pagination
 * Contains:
 * - totalItems: The total number of items found
 * - items: Iterable containing items on the current page
 * - currentPage: current page
 * - totalPages: the total number of page found
 * @param <T>
 */
public class PaginationResult<T> {
    private long totalItems;
    private Iterable<T> items;
    private long currentPage;
    private long totalPages;

    public PaginationResult() {
    }

    public PaginationResult(long totalItems, Iterable<T> items, long currentPage, long totalPages) {
        this.totalItems = totalItems;
        this.items = items;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Iterable<T> getItems() {
        return items;
    }

    public void setItems(Iterable<T> items) {
        this.items = items;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
