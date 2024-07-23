import styled from "styled-components";
import media from "@/styles/media";

export const BackDrop = styled.div`
  position: fixed;
  top: 0;
  left: 0;

  width: 100vw;
  height: 100vh;

  background-color: #00000059;
`;

export const ModalContent = styled.div`
  overflow: hidden auto;
  padding: 2rem;
  background: ${({ theme }) => theme.COLOR.white};

  ::-webkit-scrollbar {
    width: 7px;
  }

  ::-webkit-scrollbar-thumb {
    height: 30%;
    background: rgb(132 174 225 / 70%);
    border-radius: 10px;
  }

  ::-webkit-scrollbar-track {
    background: rgb(132 174 225 / 20%);
  }

  ::-webkit-scrollbar-button:increment,
  ::-webkit-scrollbar-button {
    display: block;
    height: 25px;
  }

  ::-webkit-scrollbar-button:decrement {
    display: block;
    height: 25px;
  }

  ${media.small`
    position: fixed;
    left: 0;
    bottom: 0;
    width: 100vw;
    height: 70vh;
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
