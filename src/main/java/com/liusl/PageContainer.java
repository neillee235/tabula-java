package com.liusl;

import technology.tabula.Page;

import java.util.List;


public class PageContainer {

    private Page page ;

    private List<Block> textBlockList;
    private List<Block> tableBlockList;

    public List<Block> getTextBlockList() {
        return textBlockList;
    }

    public void setTextBlockList(List<Block> textBlockList) {
        this.textBlockList = textBlockList;
    }

    public List<Block> getTableBlockList() {
        return tableBlockList;
    }

    public void setTableBlockList(List<Block> tableBlockList) {
        this.tableBlockList = tableBlockList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
