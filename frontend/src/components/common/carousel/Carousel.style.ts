import styled from "styled-components";

export const CarouselContainer = styled.div`
  position: relative;
  overflow-x: hidden;
  min-width: 100%;
  margin: 0 auto;
`;

export const CarouselWrapper = styled.div<{ $currentIndex: number }>`
  transform: ${({ $currentIndex }) => `translateX(-${$currentIndex * 100}%)`};
  display: flex;
  transition: transform 0.7s ease-in-out;
`;

export const CarouselItem = styled.div`
  display: flex;
  justify-content: center;
  box-sizing: border-box;
  min-width: 100%;
`;

export const CarouselLeftButton = styled.button<{ isLast: boolean }>`
  position: absolute;
  top: 50%;

  color: ${({ theme, isLast }) => (isLast ? theme.COLOR.grey1 : theme.COLOR.black)};

  background: transparent;
  outline: none;
`;

export const CarouselRightButton = styled.button<{ isLast: boolean }>`
  position: absolute;
  top: 50%;
  right: 0;

  color: ${({ theme, isLast }) => (isLast ? theme.COLOR.grey1 : theme.COLOR.black)};

  background: transparent;
  outline: none;
`;
