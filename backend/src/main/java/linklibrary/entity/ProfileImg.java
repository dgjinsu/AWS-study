package linklibrary.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileImg {

    @Id
    @GeneratedValue
    @Column(name = "profile_img_id")
    private Long id;

    private String storeFileName; //저장할때 쓰는 파일 이름

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
