import styled from "styled-components";
import media from "@/styles/media";

export const BannerContainer = styled.div`
  display: flex;
  gap: 10rem;
  align-items: center;
  justify-content: center;

  width: 100vw;
  height: 400px;
  margin: -113px calc(-50vw + 50%) 2rem;

  font-family: "Do Hyeon", sans-serif;

  background: linear-gradient(to right, rgb(255 250 245 / 100%), rgb(230 230 255 / 100%));

  ${media.small`
    display:none;
  `}
  ${media.medium`
    gap: 1rem;
  `};
`;

export const ImageWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  height: 100%;
  margin-top: 65px;

  img {
    width: 20rem;
  }

  ${media.medium`
    img {
      width: 18rem;
    }
  `}
`;

export const BannerTextContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  height: 100%;
  margin-top: 65px;
`;

export const BannerText = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100px;

  color: ${({ theme }) => theme.COLOR.white};
  text-shadow:
    2px 2px 5px rgb(0 0 0 / 40%),
    4px 4px 10px rgb(0 0 0 / 20%);

  span {
    font: ${({ theme }) => theme.TEXT.xLarge_bold};
  }

  ${media.medium`
    span {
      font: ${({ theme }) => theme.TEXT.large_bold};
    }
  `}
`;
