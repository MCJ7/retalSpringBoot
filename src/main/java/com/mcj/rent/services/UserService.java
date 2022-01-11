package com.mcj.rent.services;

import com.mcj.rent.entities.PicEntity;
import com.mcj.rent.entities.UserEntity;
import com.mcj.rent.enums.RoleEnum;
import com.mcj.rent.exception.ValidationException;
import com.mcj.rent.repositories.UserRepository;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author maxco
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired(required = true)
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    private final String MESSAGE_USERNAME = "The user %s not exist ";
    @Autowired
    private PicService picService;
    @Transactional
    public UserEntity create(MultipartFile file,String userName, String email, 
            String pass, String role) throws Exception {
        if (repository.existsUserByUserName(userName)) {
            throw new ValidationException("The user is not available");
        }
        UserEntity entity = new UserEntity();
        for (UserEntity userEntity : repository.findAll()) {
            System.out.println(userEntity);
        }

        entity.setEmail(email);
        entity.setEnable(Boolean.TRUE);
        entity.setPass(encoder.encode(pass));
        entity.setUserName(userName);
        entity.setRole(RoleEnum.valueOf(role));
        PicEntity pic = picService.save(file);
        entity.setAvatar(pic);
        return repository.save(entity);
    }

    @Transactional
    public void modify(MultipartFile file,String userName, String email) throws Exception {
        UserEntity entity = session();
        entity.setEmail(email);
        entity.setEnable(Boolean.TRUE);
        entity.setUserName(userName);
        String picId = null;
        if(entity.getAvatar()!=null){
            picId = entity.getAvatar().getId();
        }
        PicEntity pic = picService.modify(picId, file);
        entity.setAvatar(pic);
        repository.save(entity);
    }
    @Transactional
    public void modifyPassword(String pass) throws Exception {
        UserEntity entity = session();
        entity.setPass(encoder.encode(pass));
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> list() {
        return repository.findAll();
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserEntity findByUserName(String userName) {
        return repository.findByUserName(userName).get();
    }

    @Transactional(readOnly = true)
    public UserEntity lastUser() {

        List<UserEntity> users = repository.findAll(Sort.by(Sort.Direction.DESC, "signDate"));
        return users.get(0);
    }

    @Transactional(readOnly = true)
    public UserEntity searchId(String id) {
        Optional<UserEntity> commentEntity = repository.findById(id);
        return commentEntity.get();
    }

    @Transactional(readOnly = true)
    public UserEntity searchUserName(String userName) {
        Optional<UserEntity> entity = repository.findByUserName(userName);
        return entity.get();
    }

    @Transactional(readOnly = true)
    public UserEntity session() {
        Authentication authentication = SecurityContextHolder.
                getContext().getAuthentication();
        String currentUser = authentication.getName();
        UserEntity user = findByUserName(currentUser);
        return user;
    }

    @Transactional
    public void enable(String id) {
        UserEntity u = repository.findById(id).get();
        if (u.getEnable()) {
            u.setEnable(Boolean.FALSE);
        } else {
            u.setEnable(Boolean.TRUE);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity usuario = repository.findByUserName(userName).orElseThrow(() -> {
            return new UsernameNotFoundException(String.format(MESSAGE_USERNAME, userName));
        });

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRole());

        return new User(usuario.getUserName(), usuario.getPass(), Collections.singletonList(authority));

    }

}
