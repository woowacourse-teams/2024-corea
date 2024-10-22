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
  z-index: 999;
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
  z-index: 1000;
  top: 0;
  right: ${({ $isOpen }) => ($isOpen ? "0" : "-60%")};

  width: 60%;
  height: 100vh;

  background-color: ${({ theme }) => theme.COLOR.white};

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

  padding: 1rem;

  background-color: ${({ theme }) => theme.COLOR.primary2};
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
  gap: 0.4rem;
  color: ${({ theme }) => theme.COLOR.white};

  strong {
    font: ${({ theme }) => theme.TEXT.medium_bold};
  }

  span {
    font: ${({ theme }) => theme.TEXT.semiSmall};
  }
`;

export const NavItems = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;

  width: 100%;
  padding: 1rem;

  list-style-type: none;
`;

export const NavItem = styled.li`
  cursor: pointer;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 42px;

  font: ${({ theme }) => theme.TEXT.medium};
  color: ${({ theme }) => theme.COLOR.black};
  text-align: center;

  &:hover,
  &:focus {
    background-color: ${({ theme }) => theme.COLOR.grey0};
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
