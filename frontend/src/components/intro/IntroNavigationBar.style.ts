import styled from "styled-components";
import media from "@/styles/media";

export const NavContainer = styled.div`
  position: fixed;
  top: 50%;
  right: calc((100vw - 1000px) / 2 - 10px);
  transform: translateY(-50%);

  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;

  @media (width >= 1300px) {
    right: calc((100vw - 1200px) / 2 - 10px);
  }

  ${media.medium`
    right: calc((100vw - 580px) / 2 - 10px);  
  `}

  ${media.small`
    display: none;
  `}
`;

interface NavDotProps {
  active: boolean;
}

export const NavDot = styled.div<NavDotProps>`
  cursor: pointer;

  width: 10px;
  height: 10px;

  background-color: ${({ theme, active }) => (active ? theme.COLOR.grey3 : theme.COLOR.grey1)};
  border-radius: 50%;

  transition: all 0.3s ease;

  &:hover {
    background-color: #fff;
  }
`;
