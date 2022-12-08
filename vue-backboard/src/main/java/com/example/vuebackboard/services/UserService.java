package com.example.vuebackboard.services;

import com.example.vuebackboard.entity.BoardEntity;
import com.example.vuebackboard.entity.UserEntity;
import com.example.vuebackboard.entity.UserRepository;
import com.example.vuebackboard.web.dtos.BoardDto;
import com.example.vuebackboard.web.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (userEntity.getUserId().equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            if (userEntity.getRole().equals("ROLE_ADMIN")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }

        return new User(userEntity.getUserId(), userEntity.getUserPw(), authorities);
    }

    public UserEntity userCreate(UserDto userDto) {
        String encPassword = passwordEncoder.encode(userDto.getUserPw());
        UserEntity userEntity = UserEntity.builder()
                .userId(userDto.getUserId())
                .userPw(encPassword)
                .userName(userDto.getUserName())
                .build();
        userEntity.setRole("ROLE_USER");


        return userRepository.save(userEntity);
    }

    public UserDto getUser(Long id) {
        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return UserDto.builder()
                .idx(entity.getIdx())
                .userId(entity.getUserId())
                .userName(entity.getUserName())
                .role(entity.getRole())
                .build();
    }
}
