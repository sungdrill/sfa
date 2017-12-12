package com.cellbiotech.util;

/**
 * 페이징 클래스
 */
public class PagingUtil {

    private int PAGE_SIZE ;
    private int totalCount;
    private int currentPageNum;



    public PagingUtil(int totalCount, int currentPageNum) {
        this.totalCount = totalCount;
        this.currentPageNum = currentPageNum;
    }

    public void setPageSize(int pageSize){
        this.PAGE_SIZE = pageSize;
    }

    public int getPageSize(){
        return PAGE_SIZE;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }

    public int getStartPageNumForBoard() {
        return getStartPageNum() - 1 <= 0 ? 0 : getStartPageNum() - 1;
    }

    public int getStartPageNum() {
        return totalCount == 0 ? 0 : ((currentPageNum - 1) * PAGE_SIZE) + 1;
    }

    public int getEndPageNum() {
        return (getStartPageNum() + PAGE_SIZE - 1) > totalCount ? totalCount
                : (getStartPageNum() + PAGE_SIZE - 1);
    }

    public int getPrePageNum() {
        return (currentPageNum - 1) <= 0 ? 1 : (currentPageNum - 1);
    }

    public int getNextPageNum() {
        return (currentPageNum + 1) > getTotalPages() ? getTotalPages()
                : (currentPageNum + 1);
    }

    public int getRealStartPageNum() {
        return (totalCount - ((currentPageNum - 1) * PAGE_SIZE));
    }

    public int getRealEndPageNum() {
        return getRealStartPageNum() - PAGE_SIZE + 1 < 0 ? 1
                : getRealStartPageNum() - PAGE_SIZE + 1;
    }

}
