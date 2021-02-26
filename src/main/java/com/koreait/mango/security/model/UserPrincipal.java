package com.koreait.mango.security.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.koreait.mango.model.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter																							// 시큐리티를 사용할 경우 무조건 있어야한다
public class UserPrincipal extends UserEntity implements OAuth2User, UserDetails {
//	private UserEntity entity;

//	여러 개의 권한을 저장할 변수이지만 하나의 권한 값만 들어간다
	private Collection<? extends GrantedAuthority> authorities;
    
	private Map<String, Object> attributes; // oauth2 관련 변수
    
//	user 값들을 저장 
    private UserPrincipal(UserEntity user) {
    	this.setUserPk(user.getUserPk());
    	this.setUid(user.getUid());
    	this.setUpw(user.getUpw());
    	this.setEmail(user.getEmail());
    	this.setAuth(user.getAuth());
//    	this.entity = user;
    	
//    	권한 만듦
    	authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getAuth()));
    }
    
// oauth2 관련
    public static UserPrincipal create(UserEntity user,  Map<String, Object> attributes) {
    	 UserPrincipal userPrincipal = UserPrincipal.create(user);
         userPrincipal.setAttributes(attributes);
         return userPrincipal;
    }
   
// 시큐리티 폼 로그인 관련
    public static UserPrincipal create(UserEntity user) {
        List<GrantedAuthority> authorities = Collections.
                												singletonList(new SimpleGrantedAuthority(user.getAuth()));
        return new UserPrincipal(user);
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
//	비밀번호
	@Override
	public String getPassword() {		
		return this.getUpw();
	}
	
//	아이디 
	@Override
	public String getUsername() {
		return this.getUid();
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

	@Override
	public Map<String, Object> getAttributes() {		
		return attributes;
	}

//	oauth2
	@Override
	public String getName() {
		return this.getUid();
	}

}
