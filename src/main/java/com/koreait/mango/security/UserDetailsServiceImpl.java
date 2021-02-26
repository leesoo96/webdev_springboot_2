package com.koreait.mango.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.koreait.mango.HomeMapper;
import com.koreait.mango.model.UserEntity;
import com.koreait.mango.security.model.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private HomeMapper mapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
//	폼로그인 사용
	@Override												// username
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		return loadUserByUsername("mango", uid);
	} 

//	폼로그인 & oauth2 사용
	public UserDetails loadUserByUsername(String provider, String uid) throws UsernameNotFoundException {
		UserEntity p = new UserEntity();
		p.setProvider(provider);
		p.setUid(uid);	
		UserEntity ue = mapper.selUser(p);
		if(ue == null) {
			return null;
		}
		return UserPrincipal.create(ue); // 로그인 성공 
	}
	
//	회원가입 - 폼로그인 & oauth2 
	public int join(UserEntity p) {
		if(p.getUpw() != null && !"".equals(p.getUpw())) {
			p.setUpw(encoder.encode(p.getUpw())); // 비밀번호 암호화 
		}
		return mapper.insUser(p); 
//		소셜로그인은 암호화할 필요가 없기때문에 별도의 암호화를 해주지않는다 
//		upw -> null 로 들어감   
	}
}
