import styled, { keyframes } from "styled-components";
import media from "@/styles/media";

export const toastSlideIn = keyframes`
  from{
    opacity: 0;
  }to{
    opacity: 1;
  }
`;

export const toastSlideOut = keyframes`
  from{
    opacity: 1;
  }to{
    opacity: 0;
  }
`;

export const Wrapper = styled.div<{ closeAnimation: boolean }>`
  position: fixed;
  right: 30px;
  bottom: 30px;

  display: flex;
  align-items: center;
  justify-content: center;

  min-width: 350px;
  height: 35px;

  font-weight: 700;
  color: ${({ theme }) => theme.COLOR.white};

  background-color: ${({ theme }) => theme.COLOR.primary2};
  border-radius: 6px;

  ${media.small`
    width: 80%;
    right: 0;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    `}

  animation: ${({ closeAnimation }) => (closeAnimation ? toastSlideOut : toastSlideIn)} 0.4s
    ease-in-out forwards;
`;
