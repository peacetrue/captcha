package com.github.peacetrue.image;

import lombok.*;

import java.io.Serializable;

/**
 * 字体
 *
 * @author peace
 * @since 1.0
 **/
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Front implements Serializable {

    private String name;
    private Integer style;
    private Integer size;

    public java.awt.Font toFont() {
        return new java.awt.Font(name, style, size);
    }
}
