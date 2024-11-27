package com.github.shopping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Character gender;  // 성별 (예: 'M', 'F')

    @Column(name = "phone_num")
    private String phoneNum;

    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserPrincipal> userPrincipals = new ArrayList<>();  // UserPrincipal과의 관계 설정
}
