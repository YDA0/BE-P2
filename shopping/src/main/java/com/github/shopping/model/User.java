package com.github.shopping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column
    @Pattern(regexp = "^[mMfF]$", message = "성별은 'M' 또는 'F'만 입력 가능합니다.(소문자도 가능합니다.)")
    private String gender;  // 성별 (예: 'M', 'm', 'F', 'f')

    @Column(name = "phone_num")
    @NotBlank(message = "전화번호를 입력하세요")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 잘못되었습니다")
    private String phoneNum;

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email(message = "유효한 이메일 형식이 아닙니다")
    @Size(max = 50, message = "이메일은 최대 50자까지 입력 가능합니다")
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserPrincipal> userPrincipals = new ArrayList<>();  // UserPrincipal과의 관계 설정
}
