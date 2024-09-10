import styled from "styled-components";
import media from "@/styles/media";

export const BackDrop = styled.div`
  position: fixed;
  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;

  opacity: 0.4;
  background-color: ${({ theme }) => theme.COLOR.black};
`;

export const ModalContent = styled.div`
  position: relative;
  overflow: hidden auto;
  padding: 2rem;
  background-color: ${({ theme }) => theme.COLOR.white};

  ${media.small`
    position: fixed;
    left: 0;
    bottom: 0;
    height: 70vh;
    width:100%;
    border-radius: 8px 8px 0px 0px;
  `}

  ${media.medium`
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    border-radius: 8px;
    width: 480px;
    height: 400px;
  `}

  ${media.large`
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    border-radius: 8px;
    width: 600px;
    height: 480px;
  `}
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 1rem;
  right: 1rem;

  font-size: 1.6rem;
  color: ${({ theme }) => theme.COLOR.grey2};

  background: transparent;
  border: none;
`;
