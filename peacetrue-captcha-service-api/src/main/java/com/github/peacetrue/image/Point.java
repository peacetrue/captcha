package com.github.peacetrue.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 点
 *
 * @author peace
 * @since 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point implements Serializable {

    public static final Point ZERO = new Point(0, 0);

    private Integer x;
    private Integer y;
}
