import styled, { keyframes } from "styled-components";
import { ToastType } from "@/@types/toast";
import { Z_INDEX } from "@/styles/zIndex";

export const toastSlideIn = keyframes`
  from {
    transform: translateY(0);
    opacity: 0;
  }
  to {
    transform: translateY(20px);
    opacity: 1;
  }
`;

export const toastSlideOut = keyframes`
  from {
    transform: translateY(20px);
    opacity: 1;
  }
  to {
    transform: translateY(0);
    opacity: 0;
  }
`;

export const ToastContainer = styled.div`
  position: fixed;
  z-index: ${Z_INDEX.toast};
  top: 20px;
  left: 50%;
  transform: translateX(-50%);

  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
`;

export const Wrapper = styled.div<{ $closeAnimation: boolean; $type: ToastType }>`
  display: flex;
  align-items: center;
  justify-content: center;

  min-width: 300px;
  max-width: 80vw;
  padding: 1rem 1.2rem;

  font: ${({ theme }) => theme.TEXT.small_bold};
  line-height: normal;
  color: ${({ theme }) => theme.COLOR.white};
  word-break: keep-all;
  overflow-wrap: break-word;

  background-color: ${({ theme, $type }) =>
    $type === "error" ? theme.COLOR.error : theme.COLOR.primary2};
  border-radius: 8px;
  box-shadow: 0 4px 12px rgb(0 0 0 / 15%);

  animation: ${({ $closeAnimation }) => ($closeAnimation ? toastSlideOut : toastSlideIn)} 0.4s
    ease-in-out forwards;
`;
