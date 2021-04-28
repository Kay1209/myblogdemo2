package com.oracle.kays.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Version  //表示乐观锁字段
    @TableField(fill = FieldFill.INSERT)
    protected Integer version = 0; //乐观锁

    @TableField(fill = FieldFill.INSERT)
    @Column(length = 32)
    protected String createTime; //表示该记录的创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(length = 32)
    protected String modifyTime; //表示该记录的最后一次修改时间

    @TableLogic //表示是逻辑删除字段
    @TableField(fill = FieldFill.INSERT)
    protected boolean flag = true; //true,表示记录没有被删除，false表示该记录已经被删除。
    //体现在数据库中，1表是没有被删除，0表示已经被删除。
   /* public String getCreateTime() {
        DateTime dt = DateTime.of(new Date());
        createTime = dt.toString("yyyy-MM-dd HH:mm:ss");
        return createTime;
    }

    public String getModifyTime() {
        DateTime dt = DateTime.of(new Date());
        modifyTime = dt.toString("yyyy-MM-dd HH:mm:ss");
        return modifyTime;
    }*/

}
