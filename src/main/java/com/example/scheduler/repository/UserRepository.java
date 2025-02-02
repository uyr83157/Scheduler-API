package com.example.scheduler.repository;

import com.example.scheduler.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepository {

    // JdbcTemplate 의존성 주입
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // 유저 등록
    public int addUser(User user) {

        // 클라이언트에게 요청 인자로  name, email, user_account, user_password 을 받고,
        // user_created_at, user_updated_at 는 현재 시간으로 받는 sql 쿼리문 작성
        // 플레이스홀더 적용
        String sql = "insert into users (name, email, user_account, user_password, user_created_at, user_updated_at) " +
                "values (?, ?, ?, ?, NOW(), NOW())";

        // SQL 쿼리 실행 후 영향받은 행의 수를 int 타입으로 반환
        // 성공하면 추가하는 행의 개수가 1개 이므로 1반환, 실패하면 0반환
        // 끝에 `> 0;` 넣어서 boolean 타입으로 반환 시킬 수 도 있음
        return jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getUserAccount(),
                user.getUserPassword());
    }

    // userAccount 로 특정 유저 조회
    public User findUserByAccount(String userAccount) {
        String sql = "select * from users where user_account = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class),
                    userAccount);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // email 로 특정 유저 조회
    public User findUserByEmail(String email) {
        String sql = "select * from users where email = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class),
                    email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    // 특정 유저 정보 수정
    public int updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, user_password = ?, user_updated_at = NOW() WHERE user_account = ?";

        return jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getUserPassword(),
                user.getUserAccount());
    }

    // 특정 유저 정보 삭제
    public int deleteUserById(Long userId) {
        String sql = "delete from users where user_id = ?";
        return jdbcTemplate.update(sql, userId);
    }


}
