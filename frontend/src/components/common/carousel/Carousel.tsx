import React, { Children, ReactNode, isValidElement, useState } from "react";
import * as S from "@/components/common/carousel/Carousel.style";
import Icon from "@/components/common/icon/Icon";

interface CarouselProps {
  children: ReactNode;
}

const Carousel = ({ children }: CarouselProps) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  const validChildren = Children.toArray(children).filter(isValidElement);

  const nextSlide = () =>
    setCurrentIndex((prevIndex) => Math.min(prevIndex + 1, validChildren.length - 1));

  const prevSlide = () => setCurrentIndex((prevIndex) => Math.max(prevIndex - 1, 0));

  return (
    <S.CarouselContainer>
      <S.CarouselWrapper $currentIndex={currentIndex}>
        {validChildren.map((child, index) => (
          <S.CarouselItem key={index}>{child}</S.CarouselItem>
        ))}
      </S.CarouselWrapper>
      <S.CarouselLeftButton
        onClick={prevSlide}
        disabled={currentIndex === 0}
        isLast={currentIndex === 0}
      >
        <Icon kind="arrowLeft" size={30} />
      </S.CarouselLeftButton>
      <S.CarouselRightButton
        onClick={nextSlide}
        disabled={currentIndex === validChildren.length - 1}
        isLast={currentIndex === validChildren.length - 1}
      >
        <Icon kind="arrowRight" size={30} />
      </S.CarouselRightButton>
    </S.CarouselContainer>
  );
};

export default Carousel;
