import styled from "styled-components";

export const NavContainer = styled.div`
  position: fixed;
  top: 50%;
  right: 12%;
  transform: translateY(-50%);

  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;
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
