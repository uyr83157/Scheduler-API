package com.example.scheduler.repository;

import com.example.scheduler.entity.User;
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

        return jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getUserAccount(),
                user.getUserPassword());
    }

    // 특정 유저 정보 조회
    public User findUserById(Long userId) {
        String sql = "select * from users where id = ?";

        // queryForObject: SQL 결과가 한 행일 때만 정상반환 시키는 jdbcTemplate 메서드
        // 결과가 없으면 EmptyResultDataAccessException 예외
        // 결과가 여러 행이면 IncorrectResultSizeDataAccessException 예외
        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(User.class),
                userId);
        // new BeanPropertyRowMapper<>(User.class): 여러 타입의 데이터를 하나의 객체로 매핑해줌(로우 매핑)
    }

    // 특정 유저 정보 수정
    public int updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, user_password = ?, user_updated_at = NOW() WHERE user_id = ?";

        return jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getUserPassword());
    }

    // 특정 유저 정보 삭제
    public int deleteUserById(Long userId) {
        String sql = "delete from users where id = ?";
        return jdbcTemplate.update(sql, userId);
    }



}
