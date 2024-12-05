package com.github.shopping.service;

import com.github.shopping.dto.UserInfoDto;


public interface MyPageService {

    UserInfoDto getUserInfo(String email); // 이메일로 유저정보 조회
}
