package com.github.shopping.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserInfoDto {
    private String name;  
    private String address; 
    private String phoneNum; 
    private String email;
    private String profileImgUrl; // 유저 프로필사진
}
