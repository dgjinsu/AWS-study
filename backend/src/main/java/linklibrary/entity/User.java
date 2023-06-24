package linklibrary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="Users")
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;


    private String loginId;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // USER, ADMIN

    private String nickname;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) //이건 무조건 REMOVE 만 됨 //이제 상관 없음
    private ProfileImg profileImg;
}
