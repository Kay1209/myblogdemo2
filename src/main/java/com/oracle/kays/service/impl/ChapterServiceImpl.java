package com.oracle.kays.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oracle.kays.entity.Chapter;
import com.oracle.kays.mapper.ChapterMapper;
import com.oracle.kays.repository.ChapterRepository;
import com.oracle.kays.service.ChapterService;
import com.oracle.kays.view.ChapterTreeMenu;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChapterServiceImpl extends BaseServiceImpl<Chapter, Integer, ChapterRepository, ChapterMapper> implements ChapterService {

    /*
    @Resource
    private ChapterRepository chapterRepository;
    @Resource
    private ChapterMapper chapterMapper;*/


    @Override
    public boolean checkHasChildrenChapter(int id) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<Chapter>();
        queryWrapper.eq("pid", id);
        Optional<List<Chapter>> optional = selectList(queryWrapper);
        return optional.isPresent();
    }

    @Override
    public Optional<List<ChapterTreeMenu>> initChapterTreeMenuByCidAndPid(String cid, int pid) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<Chapter>();
        queryWrapper.eq("cid", cid);
        queryWrapper.eq("pid", pid);
        Optional<List<Chapter>> chapterList = selectList(queryWrapper);
        if(chapterList.isPresent()){
            List<ChapterTreeMenu> chapterTreeMenuList = new ArrayList<ChapterTreeMenu>();
            for(Chapter chapter: chapterList.get()){
                ChapterTreeMenu chapterTreeMenu = new ChapterTreeMenu();
                chapterTreeMenu.setChapter(chapter);

                if(checkHasChildrenChapter(chapterTreeMenu.getChapter().getId())){
                    //这里进行递归调用....
                    int tempPid = chapterTreeMenu.getChapter().getId();
                    List<ChapterTreeMenu> childrenChapterTreeMenu = initChapterTreeMenuByCidAndPid(cid,tempPid).get();
                    chapterTreeMenu.setChildren(childrenChapterTreeMenu);
                }
                chapterTreeMenuList.add(chapterTreeMenu);
            }
            return Optional.of(chapterTreeMenuList);
        }
        return Optional.empty();
    }


}
