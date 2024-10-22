import styled from "styled-components";
import media from "@/styles/media";

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

  ${media.small`
    width: 370px;
  `}
`;

export const ImgWrapper = styled.img`
  display: flex;
  width: 30rem;
`;

export const TextContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  text-align: center;

  .main {
    margin-bottom: 1rem;
    font-family: "Do Hyeon", sans-serif;
    font-size: 4.8rem;
  }

  .sub {
    font-size: 2.4rem;
  }

  .mobile-break {
    display: none;
  }

  .desktop-only {
    font-size: 2.4rem;
    font-weight: bold;
  }

  ${media.small`
    gap: 1rem;

    .main {
      font-size: 3rem;
    }

    .sub {
      font: ${({ theme }) => theme.TEXT.medium}
    }

    .mobile-break {
      display: block;
      font-weight: bold;
    }

    .desktop-only {
      display: none;
    }
  `}
`;

export const ContentSection = styled.div`
  display: flex;
  gap: 4rem;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 700px;

  ${media.small`
    flex-direction: column;
  `}
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

  ${media.small`
    padding: 1.2rem 2rem;

    font: ${({ theme }) => theme.TEXT.semiSmall};

    &.one, &.two, &.three, &.four  {
      transform: translateX(0%);
    }
  `}
`;

export const ButtonWrapper = styled.div`
  width: 300px;
  margin-top: 8rem;

  ${media.small`
    margin-top: 0rem;
  `}
`;

export const ImgSection = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 60%;
  height: 500px;

  object-fit: contain;
  background-color: gray;

  ${media.small`
    width: 90%;
    height: 400px;
  `}
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

  p.main {
    width: 100%;
    margin-bottom: 2rem;
    font-size: 3.2rem;
    font-weight: bold;
  }

  p.sub {
    width: 100%;
    font: ${({ theme }) => theme.TEXT.xLarge};
    line-height: 2.4rem;
  }

  ${media.small`
    width: 90%;
    height: 300px;

    p.step {
      width: 100%;
      font-size: 3rem;
      font-weight: bold;
      color: ${({ theme }) => theme.COLOR.primary2};
    }

    p.main {
      width: 100%;
      margin-bottom: 2rem;
      font: ${({ theme }) => theme.TEXT.xLarge_bold};
    }

    p.sub {
      width: 100%;    
      font: ${({ theme }) => theme.TEXT.medium};
      line-height: 2.4rem;
    }
  `}
`;

export const TextSectionRight = styled(TextSection)`
  text-align: right;

  ${media.small`
    text-align: left;
    order: 2;
  `}
`;