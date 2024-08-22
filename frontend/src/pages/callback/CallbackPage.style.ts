import styled, { keyframes } from "styled-components";
import { theme } from "@/styles/theme";

export const CallbackPageContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
  justify-content: center;

  height: 100%;
`;

export const Character = styled.img`
  width: 300px;
`;

// LoadingBar
const fillBar = keyframes`
  from {
    width: 0;
  }
  to {
    width: calc(100% - 6px);
  }
`;

export const LoadingContainer = styled.div`
  position: relative;

  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;

  width: 200px;
  height: 30px;

  font: ${({ theme }) => theme.TEXT.small};
  color: ${({ theme }) => theme.COLOR.grey4};

  background-color: ${({ theme }) => theme.COLOR.white};
  border: 2px solid ${({ theme }) => theme.COLOR.primary2};
  border-radius: 25px;
`;

export const LoadingBar = styled.div`
  position: absolute;
  top: 3px;
  left: 3px;

  width: calc(100% - 6px);
  height: calc(100% - 6px);

  background-color: ${({ theme }) => theme.COLOR.primary2};
  border-radius: 10px;

  animation: ${fillBar} 2s linear;
`;
