package com.oracle.kays.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//基础视图类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseView {

    protected Integer  version; //乐观锁
    protected String createTime; //表示该记录的创建时间
    protected String modifyTime; //表示该记录的最后一次修改时间
    protected boolean flag; //表示该记录是否是已经被删除的记录。

}
