package linklibrary.mapper.Post;

import linklibrary.dto.request.PostFormDto;
import linklibrary.dto.response.CategoryDto;
import linklibrary.dto.response.MainPageDto;
import linklibrary.dto.response.PostDto1;
import linklibrary.entity.Category;
import linklibrary.entity.Post;
import linklibrary.entity.User;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

public class MainPageMapper {
    @Builder
    public static MainPageDto convertToDto(List<CategoryDto> categoryDtoList, Page<PostDto1> postDtos, long totalPost, String current) {
        MainPageDto mainPageDto = MainPageDto.builder()
                .categoryDtoList(categoryDtoList) //카테고리 리스트
                .postDtoList(postDtos)  //포스트 리스트
                .total(totalPost) //총 포스트 개수
                .currentCategory(current)  //현재 카테고리이름
                .build();
        return mainPageDto;
    }
}