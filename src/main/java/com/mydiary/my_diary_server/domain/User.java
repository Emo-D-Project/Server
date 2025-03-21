package com.mydiary.my_diary_server.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")
public class User implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;


    @Enumerated(value = EnumType.STRING)
    private OAuthType oauth;

    @Column(nullable = true)
    private String firebaseToken;

    // 비밀번호는 초기값이 0000 이다
    @Builder.Default
    @Column(nullable = true)
    private String diaryPassword = "0000";

    // 일기장 잠금 on/off 여부
    // 초기화 값은 false 이다
    @Builder.Default
    @Column(nullable = true)
    private Boolean isDiaryLock = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public User update(String username) {
        this.username = username;

        return this;
    }

    public User update(String username, String firebaseToken) {
        this.username = username;
        this.firebaseToken = firebaseToken;

        return this;
    }


    public String getDiaryPassword() {
        return this.diaryPassword;
    }

    public void setDiaryPasswordSwitch(boolean b) {
        this.isDiaryLock = b;
    }

    public boolean isDiaryPasswordSwitch() {
        return this.isDiaryLock;
    }
}
