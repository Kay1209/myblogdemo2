package com.oracle.kays.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileView extends BaseView implements Serializable {

    private String name;
    private String url;
}
