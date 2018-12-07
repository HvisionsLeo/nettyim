package com.leo.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Leo
 * @Date: 2018-12-07 上午 11:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private String userId;
    private String userName;

}
