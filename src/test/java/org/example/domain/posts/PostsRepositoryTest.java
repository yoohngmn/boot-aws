package org.example.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void load_saved_post() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        Posts posts = Posts.builder()
                .title(title)
                .content(content)
                .author("aa@bb.com")
                .build();

        postsRepository.save(posts);

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts postsResult = postsList.get(0);
        assertThat(postsResult.getTitle()).isEqualTo(title);
        assertThat(postsResult.getContent()).isEqualTo(content);
    }

    @Test
    public void should_assign_date_time() {
        // given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>> createdDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
