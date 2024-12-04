package com.github.shopping.mapper;

import com.github.shopping.dto.OptionDto;
import com.github.shopping.dto.ProductDetailDto;
import com.github.shopping.entity.Option;
import com.github.shopping.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "title", source = "product.title")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "contents", source = "product.contents")
    @Mapping(target = "productStock", source = "product.productStock")
    @Mapping(target = "productImageUrl", source = "product.productImageUrl")
    @Mapping(target = "colorOptions", source = "product.options", qualifiedByName = "toColorOptions")  // 색상 옵션 변환
    @Mapping(target = "sizeOptions", source = "product.options", qualifiedByName = "toSizeOptions")    // 사이즈 옵션 변환
    ProductDetailDto toProductDetailDto(Product product);

    // Option을 OptionDto로 매핑하는 메서드 추가
    OptionDto toOptionDto(Option option);

    // colorOptions 필터링
    @Named("toColorOptions")
    default List<OptionDto> toColorOptions(List<Option> options) {
        return options.stream()
                .filter(option -> "색상".equals(option.getOptionType()))
                .map(this::toOptionDto)
                .collect(Collectors.toList());
    }

    // sizeOptions 필터링
    @Named("toSizeOptions")
    default List<OptionDto> toSizeOptions(List<Option> options) {
        return options.stream()
                .filter(option -> "사이즈".equals(option.getOptionType()))
                .map(this::toOptionDto)
                .collect(Collectors.toList());
    }
}


