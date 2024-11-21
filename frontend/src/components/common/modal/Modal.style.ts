import styled, { css, keyframes } from "styled-components";
import media from "@/styles/media";
import { Z_INDEX } from "@/styles/zIndex";

const fadeIn = keyframes`
  0% {
    opacity: 0;
    transform: translate(-50%, -30px);
  }
  100% {
    opacity: 1;
    transform: translate(-50%, -50%);
  }
`;

const fadeOut = keyframes`
  0% {
    opacity: 1;
    transform: translate(-50%, -50%);
  }
  100% {
    opacity: 0;
    transform: translate(-50%, -30px);
  }
`;

const fadeInMobile = keyframes`
  0% {
    opacity: 0;
    transform: translateY(70px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
`;

const fadeOutMobile = keyframes`
  0% {
    opacity: 1;
    transform: translateY(0);
  }
  100% {
    opacity: 0;
    transform: translateY(70px);
  }
`;

export const BackDrop = styled.div`
  position: fixed;
  z-index: ${Z_INDEX.modalBackdrop};
  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;

  opacity: 0.4;
  background-color: ${({ theme }) => theme.COLOR.black};
`;

export const ModalContent = styled.div<{ $isVisible: boolean; $isClosing: boolean }>`
  position: relative;
  z-index: ${Z_INDEX.modal};

  overflow: hidden;

  padding: 3rem 2rem 3rem 3rem;

  background-color: ${({ theme }) => theme.COLOR.white};

  ${({ $isVisible, $isClosing }) => css`
    ${$isVisible &&
    css`
      animation: ${fadeIn} 0.5s ease backwards;
    `}

    ${$isClosing &&
    css`
      animation: ${fadeOut} 0.5s ease backwards;
    `}

    ${media.small`
      position: fixed;
      left: 0;
      bottom: 0;
      height: 70vh;
      width: 100%;
      border-radius: 8px 8px 0px 0px;      

      ${
        $isVisible &&
        css`
          animation: ${fadeInMobile} 0.5s ease backwards;
        `
      }

    ${
      $isClosing &&
      css`
        animation: ${fadeOutMobile} 0.5s ease backwards;
      `
    }
    `}

    ${media.medium`
      position: fixed;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      border-radius: 8px;
      width: 480px;
      height: 500px;
    `}

    ${media.large`
      position: fixed;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      border-radius: 8px;
      width: 600px;
      height: 580px;
    `}
  `}
`;

export const ModalBody = styled.div`
  overflow: hidden auto;
  width: 100%;
  height: 100%;
  padding-right: 1rem;
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 1rem;
  right: 1rem;

  color: ${({ theme }) => theme.COLOR.grey2};

  background: transparent;
  border: none;
`;
