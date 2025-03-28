import styled, { keyframes } from "styled-components";
import { ToastType } from "@/@types/toast";
import { Z_INDEX } from "@/styles/zIndex";

export const toastSlideIn = keyframes`
  from {
    transform: translate(-50%, 0);
    opacity: 0;
  }
  to {
    transform: translate(-50%, 20px);
    opacity: 1;
  }
`;

export const toastSlideOut = keyframes`
  from {
    transform: translate(-50%, 20px);
    opacity: 1;
  }
  to {
    transform: translate(-50%, 0);
    opacity: 0;
  }
`;

export const Wrapper = styled.div<{ $closeAnimation: boolean; $type: ToastType }>`
  position: fixed;
  z-index: ${Z_INDEX.toast};
  top: 30px;
  left: 50%;
  transform: translateX(-50%);

  display: flex;
  align-items: center;
  justify-content: center;

  min-width: 300px;
  max-width: 80%;
  min-height: 42px;
  padding: 0 1.2rem;

  font: ${({ theme }) => theme.TEXT.small_bold};
  color: ${({ theme }) => theme.COLOR.white};

  background-color: ${({ theme, $type }) =>
    $type === "error" ? theme.COLOR.error : theme.COLOR.primary2};
  border-radius: 8px;
  box-shadow: 0 4px 12px rgb(0 0 0 / 15%);

  animation: ${({ $closeAnimation }) => ($closeAnimation ? toastSlideOut : toastSlideIn)} 0.4s
    ease-in-out forwards;
`;
