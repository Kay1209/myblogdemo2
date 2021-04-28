package com.oracle.kays.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="QUESTIONTYPE")
@TableName("questiontype") //解决mybatis-plus实体类和表名不一致的情况。
@JsonIgnoreProperties
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionType extends BaseEntity implements Serializable,Cloneable {

    /**编号 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;

}
