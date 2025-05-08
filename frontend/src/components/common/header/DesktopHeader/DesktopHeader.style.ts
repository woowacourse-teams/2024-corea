import styled from "styled-components";
import media from "@/styles/media";

export const DesktopNavBarContainer = styled.ul`
  display: flex;
  gap: 1rem;
  align-items: center;

  ${media.small`
    display: none;  
  `}
`;

export const HeaderItem = styled.li`
  cursor: pointer;

  width: fit-content;
  padding: 0 0.4rem;

  font: ${({ theme }) => theme.TEXT.large};
  font-family: "Do Hyeon", sans-serif;
  color: ${({ theme }) => theme.COLOR.grey3};

  transition:
    color 0.3s,
    border-bottom 0.3s;

  &:hover,
  &.selected {
    color: ${({ theme }) => theme.COLOR.black};
    border-bottom: 3px solid ${({ theme }) => theme.COLOR.black};
  }
`;
