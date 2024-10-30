import styled from "styled-components";

export const OptionSelectContainer = styled.ul`
  position: relative;
  display: flex;
  flex-direction: row;
  border-bottom: 1px solid ${({ theme }) => theme.COLOR.grey1};
`;

export const Option = styled.li<{ $isSelected: boolean }>`
  cursor: pointer;

  box-sizing: border-box;
  width: 120px;
  padding: 0.8rem;

  font: ${({ theme }) => theme.TEXT.medium_bold};
  color: ${({ $isSelected, theme }) => ($isSelected ? theme.COLOR.black : theme.COLOR.grey3)};
  text-align: center;

  background: transparent;

  @media screen and (width <= 520px) {
    flex: 1%;
  }
`;

export const Indicator = styled.div<{ $position: number; $optionCount: number }>`
  position: absolute;
  bottom: -2px;
  left: 0;
  transform: translateX(${({ $position }) => $position * 120}px);

  width: 120px;
  height: 4px;

  background-color: ${({ theme }) => theme.COLOR.primary2};

  transition: transform 0.3s ease-in-out;

  @media screen and (width <= 520px) {
    transform: ${({ $position }) => `translateX(calc(100% * ${$position}))`};
    width: ${({ $optionCount }) => `calc(100% / ${$optionCount})`};
  }
`;
