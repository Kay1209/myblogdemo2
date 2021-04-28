package com.oracle.kays.service;



import com.oracle.kays.entity.Chapter;
import com.oracle.kays.mapper.ChapterMapper;
import com.oracle.kays.repository.ChapterRepository;
import com.oracle.kays.view.ChapterTreeMenu;

import java.util.List;
import java.util.Optional;

public interface ChapterService extends BaseService<Chapter,Integer, ChapterRepository, ChapterMapper> {


    //根据课程编程初始化课程的章节树形菜单视图。
    Optional<List<ChapterTreeMenu>> initChapterTreeMenuByCidAndPid(String cid, int pid);


    //判断是否有子章节
    boolean checkHasChildrenChapter(int id);

}
