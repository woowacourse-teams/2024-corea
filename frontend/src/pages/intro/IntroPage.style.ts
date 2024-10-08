import styled from "styled-components";

export const Layout = styled.div`
  scroll-behavior: smooth;
  scroll-snap-type: y mandatory;

  position: relative;

  overflow-y: scroll;
  display: flex;
  flex-direction: column;
  align-items: center;

  height: 100vh;
`;

export const ContentLayout = styled.div`
  scroll-snap-align: start;

  position: relative;

  display: flex;
  flex-direction: column;
  gap: 3rem;
  align-items: center;
  justify-content: center;

  width: 1400px;
  min-height: 100vh;
  padding: 10rem 0;
`;

export const ImgWrapper = styled.img`
  display: flex;
  width: 30rem;
`;

export const TextContainer = styled.p`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  text-align: center;

  .main {
    font-family: "Do Hyeon", sans-serif;
    font-size: 4.8rem;
  }

  .sub {
    font-size: 2.4rem;
  }
`;

export const ContentSection = styled.div`
  display: flex;
  gap: 4rem;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 700px;
`;

export const ContentSectionColumn = styled(ContentSection)`
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

export const ChatBubble = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  box-sizing: border-box;
  width: fit-content;
  height: fit-content;
  padding: 2rem 3.6rem;

  font: ${({ theme }) => theme.TEXT.medium};
  color: ${({ theme }) => theme.COLOR.black};
  text-align: center;

  background-color: ${({ theme }) => theme.COLOR.grey0};
  border-radius: 3.6rem;
  box-shadow: ${({ theme }) => theme.BOX_SHADOW.regular};

  &.one {
    transform: translateX(25%);
  }

  &.two {
    transform: translateX(-25%);
  }

  &.three {
    transform: translateX(25%);
  }

  &.four {
    transform: translateX(-25%);
  }
`;

export const ButtonWrapper = styled.div`
  width: 300px;
  margin-top: 8rem;
`;

export const ImgSection = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 60%;
  height: 100%;

  object-fit: contain;
  background-color: gray;
`;

export const TextSection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;

  width: 40%;
  height: 500px;

  p.step {
    width: 100%;
    font-size: 4rem;
    font-weight: bold;
    color: ${({ theme }) => theme.COLOR.primary2};
  }

  p.step:not(:first-child) {
    margin-top: 4rem;
  }

  p.main {
    width: 100%;
    margin-bottom: 2rem;
    font-size: 3.2rem;
    font-weight: bold;
  }

  p.sub {
    width: 100%;
    font: ${({ theme }) => theme.TEXT.xLarge};
  }
`;

export const TextSectionRight = styled(TextSection)`
  text-align: right;
`;
