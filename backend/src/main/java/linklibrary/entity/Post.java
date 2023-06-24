package linklibrary.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(unique = false, nullable = false)
    private String title; //제목
    @Lob //긴 문자열 받기
    private String memo; //메모
    private String url; //링크
    private boolean bookmark; //북마크 여부


    //여기서는 @JsonIgnore 안함. 한쪽만 해주면 되니까 OK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String createdBy; //생성자 닉네임

//    ==연관관계 메서드 ==//
    public void setUser(User user){
        this.user = user;
        user.getPosts().add(this);
    }
    public void addCategory(Category category){
        this.category = category;
        category.getPosts().add(this);
    }

}
