import styled, { css, keyframes } from "styled-components";

const fadeIn = keyframes`
  0% {
    opacity: 0;
    transform: translateX(100%);
  }
  100% {
    opacity: 1;
    transform: translate(0);
  }
`;

const fadeOut = keyframes`
  0% {
    opacity: 1;
    transform: translate(0);
  }
  100% {
    opacity: 0;
    transform: translate(100%);
  }
`;

export const BackDrop = styled.div<{ $isOpen: boolean }>`
  position: fixed;
  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;

  visibility: ${({ $isOpen }) => ($isOpen ? "visible" : "hidden")};
  opacity: 0.4;
  background-color: ${({ theme }) => theme.COLOR.black};
`;

export const SideNavBarContainer = styled.div<{ $isOpen: boolean; $isClosing: boolean }>`
  position: fixed;
  top: 0;
  right: ${({ $isOpen }) => ($isOpen ? "0" : "-60%")};

  overflow: hidden auto;

  width: 60%;
  height: 100vh;

  background-color: ${({ theme }) => theme.COLOR.white};
  border-radius: 10px 0 0 10px;

  ${({ $isOpen, $isClosing }) => css`
    ${$isOpen &&
    css`
      animation: ${fadeIn} 0.5s ease backwards;
    `}

    ${$isClosing &&
    css`
      animation: ${fadeOut} 0.5s ease backwards;
    `}
  `}
`;

export const TopSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  padding: 2rem;

  background: linear-gradient(to right, rgb(255 250 245 / 100%), rgb(230 230 255 / 100%));
`;

export const ProfileWrapper = styled.div`
  display: flex;
  gap: 1rem;
  align-items: center;
  width: 100%;
`;

export const ProfileInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;

  font: ${({ theme }) => theme.TEXT.medium_bold};
  color: ${({ theme }) => theme.COLOR.black};
`;

export const NavItems = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;

  width: 100%;
  padding: 2rem;

  list-style-type: none;
`;

export const NavItem = styled.li`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 42px;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.black};
  text-align: center;

  transition: 0.4s background-color;

  &:hover,
  &:focus {
    border: 1px solid ${({ theme }) => theme.COLOR.grey1};
    border-radius: 5px;
  }

  a {
    display: flex;
    align-items: center;
    justify-content: center;

    width: 100%;
    height: 100%;

    color: inherit;
    text-decoration: none;
  }
`;

export const LogoutButton = styled(NavItem)`
  color: ${({ theme }) => theme.COLOR.black};
`;
