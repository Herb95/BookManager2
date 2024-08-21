package com.graywolfz.bms.model;

public class BookType {
    private Integer booktypeId;

    private String bookTypeName;

    private String bookTypeDesc;

    public Integer getBooktypeId() {
        return booktypeId;
    }

    public void setBookTypeId(Integer bookTypeId) {
        this.booktypeId = bookTypeId;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public String getBookTypeDesc() {
        return bookTypeDesc;
    }

    public void setBookTypeDesc(String bookTypeDesc) {
        this.bookTypeDesc = bookTypeDesc;
    }
}
