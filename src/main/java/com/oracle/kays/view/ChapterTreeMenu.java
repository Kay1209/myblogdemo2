package com.oracle.kays.view;


import com.oracle.kays.entity.Chapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
//没一门课程对应的章节树形菜单视图
public class ChapterTreeMenu {

    private Chapter chapter; //父章节对象
    private int id; //章节编号
    private int pid; //父类章节编号
    private int level; //菜单的层数
    private int articleId; //文章编号
    private String label; //章节名称
    private boolean isLeaf;
    /*
     * 规定树形菜单最多就是三层。
     *
     * */
    private List<ChapterTreeMenu> children; //子章节的集合

    public int getId() {
        return chapter.getId();
    }

    public int getPid() {
        return chapter.getPid();
    }

    public int getLevel() {
        return chapter.getLevel();
    }

    public int getArticleId() {
        return chapter.getArticleId();
    }

    public String getLabel() {
        return chapter.getCname();
    }

    public boolean isLeaf() {
        return children==null;
    }
}
