import styled, { keyframes } from "styled-components";

export const CallbackPageContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
  justify-content: center;

  margin-top: 20%;
`;

export const Character = styled.img`
  width: 50%;
  max-width: 250px;
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

  background-color: ${({ theme }) => theme.COLOR.white};
  border: 2px solid ${({ theme }) => theme.COLOR.primary2};
  border-radius: 25px;

  p {
    z-index: 999;
    font: ${({ theme }) => theme.TEXT.medium_bold};
    color: ${({ theme }) => theme.COLOR.grey4};
  }
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
